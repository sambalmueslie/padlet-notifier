package de.sambalmueslie.padlet.sniffer.db

import javax.persistence.*

@Entity(name = "SniffWallEntry")
@Table(name = "sniff_wall_entry")
data class SniffWallEntry(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long,
    @Column
    var wallId: Long,
    @Column
    var link: String,
    @Column
    var author: String,
)
