package de.sambalmueslie.padlet.update.db

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import java.time.LocalDateTime
import javax.persistence.*

@Entity(name = "Wall")
@Table(name = "wall")
data class WallData(
    @Id
    var id: Long,
    @Column
    var active: Boolean,
    @Column
    var initialized: Boolean = false
) {

    companion object {
        private val mapper = ObjectMapper().registerKotlinModule()
    }

    @Transient
    var enabledSectionIds = mutableSetOf<Long>()
        get() {
            return field ?: parseSections(sections)
        }

    @Column
    var sections: String = ""
        set(value) {
            parseSections(value)
            field = value
        }

    private fun parseSections(value: String): MutableSet<Long> {
        enabledSectionIds = mapper.readValue(value) ?: mutableSetOf()
        return enabledSectionIds
    }

    @Column
    val created: LocalDateTime? = null

    @Column
    val modified: LocalDateTime? = null
}
