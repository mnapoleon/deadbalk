package org.napoleon.deadbalk.services

import org.napoleon.deadbalk.models.Position
import org.springframework.stereotype.Component

@Component
class PositionGenerator {

    companion object {
        fun getRandomPosition(): Position =
            Position.values().toList().shuffled().first()

        fun positionFromDiceRole(diceRoll: Int): Position =
            when(diceRoll) {
                1 -> Position.UTILITY
                2 -> Position.CATCHER
                3 -> Position.FIRSTBASE
                4 -> Position.SECONDBASE
                5 -> Position.THIRDBASE
                6 -> Position.SHORTSTOP
                7 -> Position.LEFTFIELD
                8 -> Position.CENTERFIELD
                9 -> Position.RIGHTFIELD
                in 10..15 -> Position.STARTING_PITCHER
                in 16 .. 20 -> Position.RELIEF_PITCHER
                else -> Position.RELIEF_PITCHER
            }
    }
}