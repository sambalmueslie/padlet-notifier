package de.sambalmueslie.padlet.sender.db

import java.time.LocalDateTime
import javax.persistence.*

@Entity(name = "TeamsWallConfig")
@Table(name = "teams_wall_config")
data class TeamsWallConfig(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long,
    @Column(unique = true)
    var wallId: Long,
    @Column
    var active: Boolean,
    @Column
    var url: String
) {

    @Column
    val created: LocalDateTime? = null

    @Column
    val modified: LocalDateTime? = null
}
