package de.sambalmueslie.padlet.sender


//import org.ehcache.Cache
//import org.ehcache.config.builders.CacheConfigurationBuilder
//import org.ehcache.config.builders.CacheManagerBuilder.newCacheManagerBuilder
//import org.ehcache.config.builders.ExpiryPolicyBuilder
//import org.ehcache.config.builders.ResourcePoolsBuilder
import de.sambalmueslie.padlet.config.TeamsConfiguration
import de.sambalmueslie.padlet.sender.card.Card
import de.sambalmueslie.padlet.sender.card.OpenUriAction
import de.sambalmueslie.padlet.sender.card.Section
import de.sambalmueslie.padlet.sender.card.UriTarget
import de.sambalmueslie.padlet.sender.db.TeamsWallConfig
import de.sambalmueslie.padlet.sender.db.TeamsWallConfigRepository
import de.sambalmueslie.padlet.update.UpdateChangeListener
import de.sambalmueslie.padlet.update.db.WallData
import de.sambalmueslie.padlet.update.db.WallEntryData
import io.micronaut.core.type.Argument
import io.micronaut.http.HttpRequest
import io.micronaut.http.client.RxHttpClient
import io.micronaut.http.client.annotation.Client
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import javax.inject.Singleton


@Singleton
class MicrosoftTeamsSender(
    @param:Client(TeamsConfiguration.TEAMS_URL) private val httpClient: RxHttpClient,
    private val repository: TeamsWallConfigRepository,
    private val config: TeamsConfiguration
) : UpdateChangeListener {

    companion object {
        val logger: Logger = LoggerFactory.getLogger(MicrosoftTeamsSender::class.java)
    }

//    private val cache: Cache<Long, TeamsWallConfig> = newCacheManagerBuilder().build(true)
//        .createCache(
//            TeamsWallConfig::class.simpleName,
//            CacheConfigurationBuilder.newCacheConfigurationBuilder(
//                Long::class.java, TeamsWallConfig::class.java,
//                ResourcePoolsBuilder.heap(100)
//            ).withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofMinutes(5))).build()
//        )

//    private var cache: LoadingCache<Long, TeamsWallConfig> = Caffeine.newBuilder()
//        .maximumSize(10000)
//        .expireAfterWrite(5, TimeUnit.MINUTES)
//        .refreshAfterWrite(1, TimeUnit.MINUTES)
//        .build { wallId -> repository.findByWallId(wallId) }

    private val queues = mutableMapOf<Long, ChangeQueue>()

    private fun getQueue(wallId: Long): ChangeQueue {
        if (!queues.containsKey(wallId)) {
            queues[wallId] = ChangeQueue(wallId)
        }
        return queues[wallId]!!
    }

    override fun notifyEntryCreated(data: WallEntryData) {
        handleChange(data, true)
    }


    override fun notifyEntryChanged(data: WallEntryData) {
        handleChange(data, false)
    }

    private fun handleChange(data: WallEntryData, created: Boolean) {
        if (!isEnabled(data.wallId)) return
        val queue = getQueue(data.wallId)
        queue.add(data, created)
        if (queue.size >= config.chunkSize) {
            flushQueue(queue)
        }
    }

    override fun notifyWallUpdateFinished(data: WallData) {
        if (!isEnabled(data.id)) return
        if (data.initialized) {
            val queue = getQueue(data.id)
            flushQueue(queue)
        } else {
            send(data.id, listOf(Card("Wall initialized ${data.id}", "Wall ${data.id} initialized")))
        }
    }

    private fun isEnabled(wallId: Long): Boolean {
        val config = get(wallId) ?: return false
        return config.active
    }

    private fun flushQueue(queue: ChangeQueue) {
        logger.debug("Flush queue with ${queue.size} elements")
        val cards = queue.getAll().mapIndexed { index, entry -> createCard(entry.data, entry.created, index == 0) }
        send(queue.wallId, cards)
        queue.clear()
    }

    private fun createCard(data: WallEntryData, created: Boolean, startGroup: Boolean): Card {
        val title = if (created) "Element added: \"${data.subject}\"" else "Element changed: \"${data.subject}\""
        return Card(
            "${data.type} ${data.id}", title,
            listOf(Section(data.headline, data.subject, "", data.body, startGroup = startGroup)),
            listOf(OpenUriAction("View in Padlet", listOf(UriTarget("default", data.permalink))))
        )
    }

    private fun send(wallId: Long, cards: List<Card>) {
        val config = get(wallId) ?: return
        cards.forEach {
            val request = HttpRequest.POST(config.url, it)
            val result = httpClient.exchange(request, Argument.STRING).blockingFirst()
            result.status.code in 200..299
        }
    }

    private fun get(wallId: Long): TeamsWallConfig? {
//        val hit = cache.get(wallId)
//        if (hit != null) return hit
        val data = repository.findByWallId(wallId) ?: return null
//        cache.put(wallId, data)
        return data
    }


}
