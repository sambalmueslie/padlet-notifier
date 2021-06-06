package de.sambalmueslie.padlet.update.db

import io.micronaut.data.annotation.Repository
import io.micronaut.data.jdbc.annotation.JdbcRepository
import io.micronaut.data.model.query.builder.sql.Dialect
import io.micronaut.data.repository.PageableRepository

@Repository
@JdbcRepository(dialect = Dialect.POSTGRES)
interface WallPageRepository : PageableRepository<WallPageData, Long> {
    fun findEtagByNext(next: String): String?
    fun findByNext(next: String): WallPageData?
}
