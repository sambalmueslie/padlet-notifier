package de.sambalmueslie.padlet.sniffer.db

import io.micronaut.data.annotation.Repository
import io.micronaut.data.repository.PageableRepository

@Repository
interface SniffWallWishEntryRepository : PageableRepository<SniffWallWishEntry, String> {

}
