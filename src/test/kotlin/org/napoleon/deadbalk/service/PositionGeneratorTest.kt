package org.napoleon.deadbalk.service

import io.kotest.core.spec.style.ShouldSpec
import io.kotest.datatest.withData
import io.kotest.matchers.collections.shouldBeIn
import io.kotest.matchers.shouldBe
import org.napoleon.deadbalk.models.Position
import org.napoleon.deadbalk.services.PositionGenerator

data class PositionGeneratorTestCase(
    val diceRoll: Int,
    val expectedPosition: Position
)

class PositionGeneratorTest: ShouldSpec() {

    val positionForEachRoll = listOf(
        PositionGeneratorTestCase(1,  Position.UTILITY),
        PositionGeneratorTestCase(2,  Position.CATCHER),
        PositionGeneratorTestCase(3,  Position.FIRSTBASE),
        PositionGeneratorTestCase(4,  Position.SECONDBASE),
        PositionGeneratorTestCase(5,  Position.THIRDBASE),
        PositionGeneratorTestCase(6,  Position.SHORTSTOP),
        PositionGeneratorTestCase(7,  Position.LEFTFIELD),
        PositionGeneratorTestCase(8,  Position.CENTERFIELD),
        PositionGeneratorTestCase(9,  Position.RIGHTFIELD),
        PositionGeneratorTestCase(10, Position.STARTING_PITCHER),
        PositionGeneratorTestCase(11, Position.STARTING_PITCHER),
        PositionGeneratorTestCase(12, Position.STARTING_PITCHER),
        PositionGeneratorTestCase(13, Position.STARTING_PITCHER),
        PositionGeneratorTestCase(14, Position.STARTING_PITCHER),
        PositionGeneratorTestCase(15, Position.STARTING_PITCHER),
        PositionGeneratorTestCase(16, Position.RELIEF_PITCHER),
        PositionGeneratorTestCase(17, Position.RELIEF_PITCHER),
        PositionGeneratorTestCase(18, Position.RELIEF_PITCHER),
        PositionGeneratorTestCase(19, Position.RELIEF_PITCHER),
        PositionGeneratorTestCase(20, Position.RELIEF_PITCHER)
    )

    init {

        context("Rolling a X die") {
            withData(
                nameFn = { "Rolling ${it.diceRoll} should generate a ${it.expectedPosition.name}" },
                positionForEachRoll
            ) { (diceRoll, expectedPosition) ->
                PositionGenerator.positionFromDiceRole(diceRoll) shouldBe expectedPosition
            }
        }

        context("PositionGenerator") {
            should("return a random position") {
                PositionGenerator.getRandomPosition() shouldBeIn Position.values().toList()
            }
        }


    }
}