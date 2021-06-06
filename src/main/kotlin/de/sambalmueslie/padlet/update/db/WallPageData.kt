package de.sambalmueslie.padlet.update.db

import java.time.LocalDateTime
import javax.persistence.*

@Entity(name = "WallPage")
@Table(name = "wall_page")
data class WallPageData(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long,
    @Column
    var next: String,
    @Column
    var etag: String
) {
    @Column
    val created: LocalDateTime? = null

    @Column
    val modified: LocalDateTime? = null
}

