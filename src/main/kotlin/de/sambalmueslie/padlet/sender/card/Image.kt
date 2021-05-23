package de.sambalmueslie.padlet.sender.card

import com.fasterxml.jackson.annotation.JsonProperty

data class Image(
    @JsonProperty("title")
    val title: String,
    @JsonProperty("image")
    val url: String,
)
