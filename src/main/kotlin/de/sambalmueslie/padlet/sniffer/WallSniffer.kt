package de.sambalmueslie.padlet.sniffer


import de.sambalmueslie.padlet.client.wishes.WishesClient
import de.sambalmueslie.padlet.config.SnifferConfiguration
import de.sambalmueslie.padlet.sniffer.db.SniffWallEntry
import de.sambalmueslie.padlet.sniffer.db.SniffWallEntryRepository
import de.sambalmueslie.padlet.sniffer.db.SniffWallWishEntry
import de.sambalmueslie.padlet.sniffer.db.SniffWallWishEntryRepository
import io.micronaut.context.annotation.Requires
import io.micronaut.http.client.exceptions.HttpClientResponseException
import io.micronaut.scheduling.annotation.Scheduled
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import javax.inject.Singleton
import kotlin.math.max
import kotlin.system.measureTimeMillis

@Singleton
@Requires(property = "sniffer.enabled", value = "true")
open class WallSniffer(
    private val client: WishesClient,
    private val repository: SniffWallEntryRepository,
    private val wishRepository: SniffWallWishEntryRepository,
    private val config: SnifferConfiguration
) {

    companion object {
        val logger: Logger = LoggerFactory.getLogger(WallSniffer::class.java)
    }

    private var currentOffset = max(config.offset, repository.findMaxWallIdByWallIdAfter(config.offset) ?: 1)

    @Scheduled(fixedDelay = "10s")
    open fun checkForUpdates() {
        logger.info("START sniffing cycle")
        val duration = measureTimeMillis {
            repeat(config.probesPerCycle) { runSniffingCycle() }
        }
        logger.info("FINISHED sniffing cycle for ${config.probesPerCycle} within $duration ms.")
    }

    private fun runSniffingCycle() {
        logger.debug("Sniff $currentOffset")
        if (!repository.existsById(currentOffset)) {
            try {
                val wall = client.fetchWishes(currentOffset)
                val wishes = wall?.page?.data
                val wish = wishes?.firstOrNull()
                if (wish != null) {
                    val permalink = wish.attributes.permalink
                    val user = permalink.replace("https://padlet.com/", "").substringBefore("/")
                    val entry = repository.save(SniffWallEntry(0, wish.attributes.wallId, permalink, user))
                    wishes.map { SniffWallWishEntry.create(it, entry) }.forEach { wishRepository.save(it) }
                }
//            } catch (ex: PersistenceException) {
//                logger.error("Failed to store data", ex)
            } catch (eh: HttpClientResponseException) {
                // intentionally left empty
            } catch (e: Exception) {
                e.cause
                logger.error("Something went wrong", e)
            }
        }
        currentOffset++
    }
}
