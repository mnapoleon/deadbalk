package org.napoleon.deadbalk.dice

import org.napoleon.deadbalk.utils.isEmptyOrBlank
import java.util.*

class Dice {
    companion object {
        fun roll(diceExpressions: List<String>): Int =
            diceExpressions.fold(0) { total, next ->
                total + rollDie(next)
            }


        private fun rollDie(dieExpression: String): Int {
            val random = Random()
            if (dieExpression.startsWith('d')) {
                if (!dieExpression.split('d')[1].isEmptyOrBlank()) {
                    return random.nextInt(dieExpression.split('d')[1].toInt()) + 1
                }
            }
            else {
                val values = dieExpression.split('d')
                val numOfDice = values[0].toInt()
                val dieType = values[1].toInt()
                return gatherRolls(numOfDice, dieType).sum()
            }
            return 0
        }

        private fun gatherRolls(numOfDice: Int, dieType: Int): List<Int> {
            val random = Random()
            return (1..numOfDice).map { random.nextInt(dieType) + 1 }
        }
    }
}
