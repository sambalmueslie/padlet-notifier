package de.sambalmueslie.padlet.config


import io.micronaut.context.annotation.ConfigurationProperties
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@ConfigurationProperties("sniffer")
class SnifferConfiguration {

    companion object {
        val logger: Logger = LoggerFactory.getLogger(SnifferConfiguration::class.java)
    }

    var enabled: Boolean = false
        set(value) {
            logger.debug("Set enabled from '$enabled' to '$value'")
            field = value
        }

    var offset: Long = 0
        set(value) {
            logger.debug("Set offset from '$offset' to '$value'")
            field = value
        }

    var probesPerCycle: Int = 4
        set(value) {
            logger.debug("Set probesPerCycle from '$probesPerCycle' to '$value'")
            field = value
        }


}
