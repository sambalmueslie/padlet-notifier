package de.sambalmueslie.padlet.sender.card

import com.fasterxml.jackson.annotation.JsonProperty

data class Section(
    @JsonProperty("activityTitle")
    val title: String,
    @JsonProperty("activitySubtitle")
    val subtitle: String,
    @JsonProperty("activityImage")
    val image: String,
    @JsonProperty("activityText")
    val text: String = "",
    @JsonProperty("facts")
    val facts: List<Fact> = emptyList(),
    @JsonProperty("images")
    val images: List<Image> = emptyList(),
    @JsonProperty("startGroup")
    val startGroup: Boolean = false,
    @JsonProperty("heroImage")
    val heroImage: Image? = null,
    @JsonProperty("potentialAction")
    val potentialAction: List<Action> = emptyList()
)
