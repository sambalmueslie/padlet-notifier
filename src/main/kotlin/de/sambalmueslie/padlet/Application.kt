package de.sambalmueslie.padlet

import io.micronaut.runtime.Micronaut.build

fun main(args: Array<String>) {
    build()
        .args(*args)
        .packages("de.sambalmueslie.padlet")
        .start()
}

