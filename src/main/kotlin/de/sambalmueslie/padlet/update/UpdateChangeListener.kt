package de.sambalmueslie.padlet.update

import de.sambalmueslie.padlet.update.db.WallData
import de.sambalmueslie.padlet.update.db.WallEntryData

interface UpdateChangeListener {
    fun notifyEntryCreated(data: WallEntryData)
    fun notifyEntryChanged(data: WallEntryData)
    fun notifyWallUpdateFinished(data: WallData)
}
