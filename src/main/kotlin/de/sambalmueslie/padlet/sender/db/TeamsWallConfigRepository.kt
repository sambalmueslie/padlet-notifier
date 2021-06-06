package de.sambalmueslie.padlet.sender.db

import io.micronaut.data.annotation.Repository
import io.micronaut.data.jdbc.annotation.JdbcRepository
import io.micronaut.data.model.query.builder.sql.Dialect
import io.micronaut.data.repository.PageableRepository

@Repository
@JdbcRepository(dialect = Dialect.POSTGRES)
interface TeamsWallConfigRepository : PageableRepository<TeamsWallConfig, Long> {
    fun findByWallId(wallId: Long): TeamsWallConfig?
}
