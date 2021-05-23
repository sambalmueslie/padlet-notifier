package de.sambalmueslie.padlet.client.wishes


import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class Position(
    @JsonProperty("left")
    val left: Any,
    @JsonProperty("top")
    val top: Any
)
