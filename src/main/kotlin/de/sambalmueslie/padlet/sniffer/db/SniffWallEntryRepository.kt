package de.sambalmueslie.padlet.sniffer.db

import io.micronaut.data.annotation.Repository
import io.micronaut.data.repository.PageableRepository

@Repository
interface SniffWallEntryRepository : PageableRepository<SniffWallEntry, Long> {
    fun findMaxWallIdByWallIdAfter(offset: Long): Long?
}
