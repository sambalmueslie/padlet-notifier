package de.sambalmueslie.padlet.sender.card

import com.fasterxml.jackson.annotation.JsonProperty


data class Header(
	@JsonProperty("name")
    val name: String,
	@JsonProperty("value")
    val value: String
)
