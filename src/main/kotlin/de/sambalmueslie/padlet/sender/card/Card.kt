package de.sambalmueslie.padlet.sender.card

import com.fasterxml.jackson.annotation.JsonProperty

data class Card(
    @JsonProperty("summary")
    val summary: String,
    @JsonProperty("title")
    val title: String,
    @JsonProperty("sections")
    val sections: List<Section> = emptyList(),
    @JsonProperty("potentialAction")
    val potentialAction: List<Action> = emptyList(),
    @JsonProperty("themeColor")
    val color: String = "0078D7",
    @JsonProperty("@type")
    val type: String = "MessageCard",
    @JsonProperty("@context")
    val context: String = "https://schema.org/extensions",
)
