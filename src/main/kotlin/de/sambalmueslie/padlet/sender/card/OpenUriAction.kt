package de.sambalmueslie.padlet.sender.card

import com.fasterxml.jackson.annotation.JsonProperty

data class OpenUriAction(
    @JsonProperty("name")
    val name: String,
    @JsonProperty("targets")
    val targets: List<UriTarget>,

    @JsonProperty("@type")
    override val type: String = "OpenUri",
) : Action
