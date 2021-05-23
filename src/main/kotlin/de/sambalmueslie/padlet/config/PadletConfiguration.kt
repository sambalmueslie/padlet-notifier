package de.sambalmueslie.padlet.config


import io.micronaut.context.annotation.ConfigurationProperties
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@ConfigurationProperties("padlet")
class PadletConfiguration {

    companion object {
        val logger: Logger = LoggerFactory.getLogger(PadletConfiguration::class.java)

        const val PADLET_API_URL = "https://api.padlet.com/api/5"
    }

}
