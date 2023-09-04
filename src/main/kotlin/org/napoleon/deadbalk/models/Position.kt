package org.napoleon.deadbalk.models

enum class Position(val shortPosition: String, val posNum: Int) {
    UTILITY("U", 0),
    STARTING_PITCHER("SP", 1),
    RELIEF_PITCHER("RP", 1),
    CATCHER("C", 2),
    FIRSTBASE("1B", 3),
    SECONDBASE("2B", 4),
    THIRDBASE("3B", 5),
    SHORTSTOP("SS", 6),
    LEFTFIELD("LF", 7),
    CENTERFIELD("CF", 8),
    RIGHTFIELD("RF", 9)
}

fun findByShortPosition(shortPosition: String): Position? =
    Position.entries.find { it.shortPosition == shortPosition }