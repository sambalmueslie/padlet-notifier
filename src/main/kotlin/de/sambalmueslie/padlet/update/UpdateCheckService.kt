package de.sambalmueslie.padlet.update


import de.sambalmueslie.padlet.client.wishes.Wish
import de.sambalmueslie.padlet.client.wishes.WishesClient
import de.sambalmueslie.padlet.client.wishes.WishesPage
import de.sambalmueslie.padlet.common.findByIdOrNull
import de.sambalmueslie.padlet.common.forEachWithTryCatch
import de.sambalmueslie.padlet.config.PadletConfiguration
import de.sambalmueslie.padlet.update.db.*
import io.micronaut.context.annotation.Context
import io.micronaut.data.model.Page
import io.micronaut.data.model.Pageable
import io.micronaut.scheduling.annotation.Scheduled
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import kotlin.system.measureTimeMillis

@Context
open class UpdateCheckService(
    private val client: WishesClient,
    private val wallRepository: WallRepository,
    private val wallPageRepository: WallPageRepository,
    private val wallEntryRepository: WallEntryRepository,
    private val listener: Set<UpdateChangeListener>,
    private val config: PadletConfiguration
) {

    companion object {
        val logger: Logger = LoggerFactory.getLogger(UpdateCheckService::class.java)
    }


    @Scheduled(initialDelay = "1s")
    open fun initialLoad() {
        checkForUpdates() { wallRepository.findByActiveTrueAndInitializedFalse(it) }
    }

    @Scheduled(cron = "0 0 * 1/1 * ?")
    open fun checkForUpdates() {
        checkForUpdates() { wallRepository.findByActiveTrue(it) }
    }

    private fun checkForUpdates(action: (p: Pageable) -> Page<WallData>) {
        var pageable = Pageable.from(0)
        var page = action.invoke(pageable)
        page.content.forEach { checkWallForUpdates(it) }

        while (page.pageNumber < page.totalPages) {
            pageable = page.pageable.next()
            page = action.invoke(pageable)
            page.content.forEach { checkWallForUpdates(it) }
        }
    }

    private fun checkWallForUpdates(wall: WallData) {
        val wallId = wall.id
        logger.debug("START:  wall for updates $wallId")
        val duration = measureTimeMillis {
            var result = client.fetchWishes(wallId) ?: return logger.error("No data found for $wallId")
            val current = "$wallId#wishes#first"
            checkWallPageForUpdates(wall, result.page, result.etag, current)

            var next = result.page.meta.next
            while (next != null) {
                result = client.fetchWishes(wallId, next) ?: return logger.error("No data found for $wallId")
                checkWallPageForUpdates(wall, result.page, result.etag, next)
                next = result.page.meta.next
            }
            listener.forEachWithTryCatch { it.notifyWallUpdateFinished(wall) }

            if (!wall.initialized) {
                wall.initialized = true
                wallRepository.update(wall)
            }
        }
        logger.debug("FINISHED: Check wall for updates $wallId within $duration ms")
    }

    private fun checkWallPageForUpdates(wall: WallData, page: WishesPage, etag: String, current: String) {
        logger.debug("Check wall page for updates ${current.replace('\n', ' ')}")
        val currentPage = wallPageRepository.findByNext(current)
        val dbEtag = currentPage?.etag
        if (etag == dbEtag) return logger.debug("Not change detected cause etag is similar $etag")

        page.data.forEach { checkWallPageEntryForUpdate(wall, it) }

        if (currentPage == null) {
            wallPageRepository.save(WallPageData(0, current, etag))
        } else {
            currentPage.etag = etag
            wallPageRepository.update(currentPage)
        }

    }

    private fun checkWallPageEntryForUpdate(wall: WallData, wish: Wish) {
        val existing = wallEntryRepository.findByIdOrNull(wish.id)
        if (existing != null) {
            val changed = existing.update(wish)
            wallEntryRepository.update(existing)
            if (changed) notifyEntryChanged(wall, existing)
        } else {
            val data = WallEntryData.create(wish)
            wallEntryRepository.save(data)
            notifyEntryCreated(wall, data)
        }
    }

    private fun notifyEntryCreated(wall: WallData, data: WallEntryData) {
        if (!wall.initialized) return
        if (wall.enabledSectionIds.isEmpty() || wall.enabledSectionIds.contains(data.wallSectionId)) {
            listener.forEachWithTryCatch { it.notifyEntryCreated(data) }
        }
    }

    private fun notifyEntryChanged(wall: WallData, data: WallEntryData) {
        if (!wall.initialized) return
        if (wall.enabledSectionIds.isEmpty() || wall.enabledSectionIds.contains(data.wallSectionId)) {
            listener.forEachWithTryCatch { it.notifyEntryChanged(data) }
        }
    }


}
