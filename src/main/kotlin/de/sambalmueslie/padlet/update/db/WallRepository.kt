package de.sambalmueslie.padlet.update.db

import io.micronaut.data.annotation.Repository
import io.micronaut.data.model.Page
import io.micronaut.data.model.Pageable
import io.micronaut.data.repository.PageableRepository

@Repository
interface WallRepository : PageableRepository<WallData, Long> {

    fun findByActiveTrue(pageable: Pageable): Page<WallData>
    fun findByActiveTrueAndInitializedFalse(pageable: Pageable): Page<WallData>
}
