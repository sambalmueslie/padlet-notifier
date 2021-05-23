package de.sambalmueslie.padlet.sender.db

import io.micronaut.data.annotation.Repository
import io.micronaut.data.repository.PageableRepository

@Repository
interface TeamsWallConfigRepository : PageableRepository<TeamsWallConfig, Long> {
    fun findByWallId(wallId: Long): TeamsWallConfig?
}
