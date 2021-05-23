package de.sambalmueslie.padlet.sender.card

interface Input {
    val type: String
    val id: String
    val isRequired: Boolean
    val title: String
    val value: String
}
