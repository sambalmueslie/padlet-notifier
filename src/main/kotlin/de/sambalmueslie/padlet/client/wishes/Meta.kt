package de.sambalmueslie.padlet.client.wishes

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class Meta(
    @JsonProperty("next")
    val next: String?
)
