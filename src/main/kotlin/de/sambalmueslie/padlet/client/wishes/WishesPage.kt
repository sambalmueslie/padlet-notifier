package de.sambalmueslie.padlet.client.wishes

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class WishesPage(
    @JsonProperty("data")
    val data: List<Wish>,
    @JsonProperty("meta")
    val meta: Meta
)
