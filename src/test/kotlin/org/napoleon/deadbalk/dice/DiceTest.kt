package org.napoleon.deadbalk.dice

import io.kotest.core.spec.style.ShouldSpec
import io.kotest.datatest.withData
import io.kotest.matchers.ints.shouldBeBetween

data class DiceTestCase(val diceExpression: List<String>, val min: Int, val max: Int)

class DiceTest: ShouldSpec() {
    val singleDiceList = listOf(
        DiceTestCase(listOf("d6"), 1, 6),
        DiceTestCase(listOf("d8"), 1, 8),
        DiceTestCase(listOf("d12"),1, 12),
        DiceTestCase(listOf("d20"),1, 20),
    )

    val twoDiceList = listOf(
        DiceTestCase(listOf("2d6"), 2, 12),
        DiceTestCase(listOf("2d8"), 2, 16),
        DiceTestCase(listOf("2d12"),2, 24),
        DiceTestCase(listOf("2d20"),2, 40),
    )

    val twoDifferentDieList = listOf(
        DiceTestCase(listOf("d6", "d12"), 2, 18),
        DiceTestCase(listOf("d4", "2d6"), 3, 16)
    )

    val threeDifferentDieList = listOf(
        DiceTestCase(listOf("2d4", "1d6", "2d8"), 5, 30),
        DiceTestCase(listOf("2d6", "2d8", "2d10"), 6, 48)
    )
    init {
        context("Rolling a X die") {
            withData(
                nameFn = { "Rolling ${it.diceExpression} should be between ${it.min} and ${it.max}" },
                singleDiceList + twoDiceList + twoDifferentDieList + threeDifferentDieList
            ) { (diceExpression, min, max) ->
                assertDiceRoll(diceExpression, min, max)
            }
        }
    }

    private fun assertDiceRoll(diceExpressions: List<String>, min: Int, max: Int) {
        for (i in 0..50) {
            val roll = Dice.roll(diceExpressions)
            roll.shouldBeBetween(min, max)
        }
    }
}
