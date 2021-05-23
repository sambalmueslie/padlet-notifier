package de.sambalmueslie.padlet.sender.card

import com.fasterxml.jackson.annotation.JsonProperty

data class HttpPostAction(
    @JsonProperty("name")
    val name: String,
    @JsonProperty("target")
    val target: String,
    @JsonProperty("headers")
    val headers: List<Header>,
    @JsonProperty("body")
    val body: String = "",
    @JsonProperty("bodyContentType")
    val bodyContentType: String = "application/json",

    @JsonProperty("@type")
    override val type: String = "HttpPOST",
) : Action
