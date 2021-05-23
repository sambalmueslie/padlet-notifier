package de.sambalmueslie.padlet.sender.card

import com.fasterxml.jackson.annotation.JsonProperty

data class UriTarget(
    @JsonProperty("os")
    val os: String,
    @JsonProperty("uri")
    val uri: String
)
