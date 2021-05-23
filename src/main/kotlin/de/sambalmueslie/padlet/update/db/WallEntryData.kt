package de.sambalmueslie.padlet.update.db

import de.sambalmueslie.padlet.client.wishes.Wish
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.ZonedDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity(name = "WallEntry")
@Table(name = "wall_entry")
data class WallEntryData(
    @Id
    var id: String,
    @Column
    var type: String,
    @Column
    var headline: String,
    @Column
    var subject: String,
    @Column
    var body: String,
    @Column
    var permalink: String,
    @Column
    var attachment: String,
    @Column
    var author: Long,

    @Column
    var createdAt: LocalDateTime,
    @Column
    var updatedAt: LocalDateTime,
    @Column
    var contentUpdatedAt: LocalDateTime,

    @Column
    var latitude: Double?,
    @Column
    var longitude: Double?,

    @Column
    var wallSectionId: Long,
    @Column
    var wallId: Long
) {
    companion object {
        fun create(wish: Wish): WallEntryData {
            val a = wish.attributes
            val p = a.locationPoint
            return WallEntryData(
                wish.id, wish.type, a.headline, a.subject, a.body, a.permalink, a.attachment, a.authorId,
                date(a.createdAt), date(a.updatedAt), date(a.contentUpdatedAt),
                p?.latitude, p?.longitude, a.wallSectionId, a.wallId
            )
        }

        private fun date(timestamp: String): LocalDateTime {
            val zoned = ZonedDateTime.parse(timestamp)
            return LocalDateTime.ofInstant(zoned.toInstant(), ZoneOffset.UTC);
        }
    }

    fun update(wish: Wish): Boolean {
        val a = wish.attributes
        val newUpdatedAt = date(a.contentUpdatedAt)
        val changed = contentUpdatedAt != newUpdatedAt

        val p = a.locationPoint
        type = wish.type
        headline = a.headline
        subject = a.subject
        body = a.body
        permalink = a.permalink
        attachment = a.attachment
        author = a.authorId
        createdAt = date(a.createdAt)
        updatedAt = date(a.updatedAt)
        contentUpdatedAt = newUpdatedAt
        wallSectionId = a.wallSectionId
        wallId = a.wallId
        latitude = p?.latitude
        longitude = p?.longitude
        return changed
    }

    @Column
    val created: LocalDateTime? = null

    @Column
    val modified: LocalDateTime? = null
}

