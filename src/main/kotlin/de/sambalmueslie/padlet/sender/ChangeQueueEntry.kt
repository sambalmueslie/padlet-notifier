package de.sambalmueslie.padlet.sender

import de.sambalmueslie.padlet.update.db.WallEntryData

data class ChangeQueueEntry(
    val data: WallEntryData,
    val created: Boolean
)
