package de.sambalmueslie.padlet.config


import io.micronaut.context.annotation.ConfigurationProperties
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import javax.validation.constraints.NotBlank

@ConfigurationProperties("teams")
class TeamsConfiguration {

    companion object {
        const val TEAMS_URL = "https://docs.microsoft.com/"
    }

    var chunkSize: Int = 4
        set(value) {
            PadletConfiguration.logger.debug("Set chunkSize from '$chunkSize' to '$value'")
            field = value
        }

    var chunkDelay: Int = 1000
        set(value) {
            PadletConfiguration.logger.debug("Set chunkDelay from '$chunkDelay' to '$value'")
            field = value
        }
}
