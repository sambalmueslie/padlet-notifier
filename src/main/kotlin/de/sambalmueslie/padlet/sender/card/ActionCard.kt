package de.sambalmueslie.padlet.sender.card

import com.fasterxml.jackson.annotation.JsonProperty

data class ActionCard(
    @JsonProperty("name")
    val name: String,
    @JsonProperty("inputs")
    val inputs: List<Input>,
    @JsonProperty("actions")
    val actions: List<Action>,

    @JsonProperty("@type")
    override val type: String = "ActionCard",
) : Action
