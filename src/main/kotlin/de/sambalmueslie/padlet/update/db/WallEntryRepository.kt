package de.sambalmueslie.padlet.update.db

import io.micronaut.data.annotation.Repository
import io.micronaut.data.jdbc.annotation.JdbcRepository
import io.micronaut.data.model.Page
import io.micronaut.data.model.Pageable
import io.micronaut.data.model.query.builder.sql.Dialect
import io.micronaut.data.repository.PageableRepository

@Repository
@JdbcRepository(dialect = Dialect.POSTGRES)
interface WallEntryRepository : PageableRepository<WallEntryData, String> {

    fun findByWallId(wallId: Long, pageable: Pageable): Page<WallEntryData>
}
