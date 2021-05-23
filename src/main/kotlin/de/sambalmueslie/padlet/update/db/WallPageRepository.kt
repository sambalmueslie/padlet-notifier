package de.sambalmueslie.padlet.update.db

import io.micronaut.data.annotation.Repository
import io.micronaut.data.repository.PageableRepository

@Repository
interface WallPageRepository : PageableRepository<WallPageData, Long> {
    fun findEtagByNext(next: String): String?
    fun findByNext(next: String): WallPageData?
}
