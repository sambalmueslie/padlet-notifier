package de.sambalmueslie.padlet.sender.card

import com.fasterxml.jackson.annotation.JsonProperty

data class MultichoiceInput(
    @JsonProperty("id")
    override val id: String,
    @JsonProperty("isRequired")
    override val isRequired: Boolean,
    @JsonProperty("title")
    override val title: String,
    @JsonProperty("value")
    override val value: String,
    @JsonProperty("choices")
    val choices: Map<String, String> = emptyMap(),
    @JsonProperty("isMultiSelect")
    val isMultiSelect: Boolean,
    @JsonProperty("style")
    val style: String = "normal",
    @JsonProperty("@type")
    override val type: String = "MultichoiceInput",
) : Input
