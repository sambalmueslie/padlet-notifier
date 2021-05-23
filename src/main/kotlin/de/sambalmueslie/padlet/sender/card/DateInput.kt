package de.sambalmueslie.padlet.sender.card

import com.fasterxml.jackson.annotation.JsonProperty

data class DateInput(
    @JsonProperty("id")
    override val id: String,
    @JsonProperty("isRequired")
    override val isRequired: Boolean,
    @JsonProperty("title")
    override val title: String,
    @JsonProperty("value")
    override val value: String,
    @JsonProperty("includeTime")
    val includeTime: Boolean,
    @JsonProperty("@type")
    override val type: String = "DateInput",
) : Input
