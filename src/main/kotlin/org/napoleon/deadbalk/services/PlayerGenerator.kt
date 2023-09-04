package org.napoleon.deadbalk.services

import org.napoleon.deadbalk.dice.Dice
import org.napoleon.deadbalk.models.PlayerDto
import org.napoleon.deadbalk.models.PlayerType
import org.napoleon.deadbalk.models.Position
import org.napoleon.deadbalk.utils.isEmptyOrBlank
import org.springframework.stereotype.Component

@Component
class PlayerGenerator(
    private val nameGenerator: NameGenerator
) {
    fun generateRandomPlayer(): PlayerDto {
        val position = PositionGenerator.positionFromDiceRole(Dice.roll(listOf("1d20")))
        val playerType = PlayerType.values().toList().shuffled().first()
        val batterTarget = generateBatterTarget(playerType)
        return PlayerDto(
            name = nameGenerator.getRandomName(),
            position = position.text,
            handedness = getHandedness(position),
            playerType = playerType,
            batterTarget = batterTarget,
            onBaseTarget = batterTarget + Dice.roll(listOf("2d4"))
        )
    }

    fun generateCompletePlayer(playerDto: PlayerDto?): PlayerDto {
        val p = PlayerDto()
        p.name = if (playerDto?.name?.isEmptyOrBlank()) nameGenerator.getRandomName() else playerDto?.name
        p.handedness = if (playerDto?.handedness.isEmptyOrBlank()) "Right" else playerDto.handedness
        p.position = if (playerDto.position.isEmptyOrBlank())
            PositionGenerator.positionFromDiceRole(Dice.roll(listOf("d20"))).text
                    else playerDto.position
        p.playerType = if (playerDto.playerType == PlayerType.UNDEFINED)
            PlayerType.values().toList().shuffled().first()
        else playerDto.playerType
        p.batterTarget = generateBatterTarget(p.playerType)
        p.onBaseTarget = p.batterTarget + Dice.roll(listOf("2d4"))
        return p
    }

    companion object {
        fun getHandedness(position: Position): String =
            when (Dice.roll(listOf("1d10"))) {
                in 1..6 -> "Right"
                in 6..9 -> "Left"
                10 -> {
                    if (position == Position.STARTING_PITCHER ||
                        position == Position.RELIEF_PITCHER
                    ) {
                        "Left"
                    }
                    else "Switch"
                }
                else -> "Right"
            }

        fun generateBatterTarget(playerType: PlayerType): Int =
            when (playerType) {
                PlayerType.PROSPECT -> Dice.roll(listOf("d6")) + 18
                PlayerType.ROOKIE -> Dice.roll(listOf("d6")) + 21
                PlayerType.VETERAN, PlayerType.UNDEFINED -> Dice.roll(listOf("d6")) + 27
                PlayerType.OLD_TIMER -> Dice.roll(listOf("d6")) + 32
            }
    }
}
