package de.sambalmueslie.padlet.update.db

import io.micronaut.data.annotation.Repository
import io.micronaut.data.model.Page
import io.micronaut.data.model.Pageable
import io.micronaut.data.repository.PageableRepository

@Repository
interface WallEntryRepository : PageableRepository<WallEntryData, String> {

    fun findByWallId(wallId: Long, pageable: Pageable): Page<WallEntryData>
}
