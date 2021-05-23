package de.sambalmueslie.padlet.sender.card

import com.fasterxml.jackson.annotation.JsonProperty

data class TextInput(
    @JsonProperty("id")
    override val id: String,
    @JsonProperty("isRequired")
    override val isRequired: Boolean,
    @JsonProperty("title")
    override val title: String,
    @JsonProperty("value")
    override val value: String,
    @JsonProperty("isMultiline")
    val isMultiline: Boolean,
    @JsonProperty("maxLength")
    val maxLength: Int,
    @JsonProperty("@type")
    override val type: String = "TextInput",
) : Input
