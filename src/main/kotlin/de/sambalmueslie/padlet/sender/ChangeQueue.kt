package de.sambalmueslie.padlet.sender


import de.sambalmueslie.padlet.update.db.WallEntryData

internal class ChangeQueue(
    val wallId: Long,
) {

    private val elements = mutableListOf<ChangeQueueEntry>()


    fun add(data: WallEntryData, created: Boolean) {
        elements.add(ChangeQueueEntry(data, created))
    }

    val size
        get() = elements.size

    fun getAll(): List<ChangeQueueEntry> = elements
    fun clear() = elements.clear()

}
