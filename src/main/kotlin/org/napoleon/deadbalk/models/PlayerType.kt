package org.napoleon.deadbalk.models

import com.fasterxml.jackson.annotation.JsonValue

enum class PlayerType(val text: String) {
    PROSPECT("Prospect"),
    ROOKIE("Rookie"),
    VETERAN("Veteran"),
    OLD_TIMER("Old Timer"),
    UNDEFINED("Undefined"),
}

fun findByText(text: String): PlayerType? =
    PlayerType.entries.find { it.text == text }