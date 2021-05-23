package de.sambalmueslie.padlet.sniffer.db

import de.sambalmueslie.padlet.client.wishes.Wish
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.ZonedDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity(name = "SniffWallWishEntry")
@Table(name = "sniff_wall_wish_entry")
data class SniffWallWishEntry(
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
    var wallId: Long,

    @Column
    var wallSniffEntryId: Long
) {
    companion object {
        fun create(wish: Wish, wallSniffEntry: SniffWallEntry): SniffWallWishEntry {
            val a = wish.attributes
            val p = a.locationPoint
            return SniffWallWishEntry(
                wish.id, wish.type, a.headline, a.subject, a.body, a.permalink, a.attachment, a.authorId,
                date(a.createdAt), date(a.updatedAt), date(a.contentUpdatedAt),
                p?.latitude, p?.longitude, a.wallSectionId, a.wallId, wallSniffEntry.id
            )
        }

        private fun date(timestamp: String): LocalDateTime {
            val zoned = ZonedDateTime.parse(timestamp)
            return LocalDateTime.ofInstant(zoned.toInstant(), ZoneOffset.UTC);
        }
    }
}

