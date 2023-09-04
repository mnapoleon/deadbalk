package org.napoleon.deadbalk.models

data class PlayerDto(
    var name: String = "",
    var position: String = "",
    var handedness: String = "",
    var playerType: PlayerType = PlayerType.UNDEFINED,
    var batterTarget: Int = 0,
    var onBaseTarget: Int = 0,
)

fun PlayerDto.toEntity(): Player = Player(
    name = name,
    position = position,
    handedness = handedness,
    playerType = playerType.text,
    batterTarget = batterTarget,
    onBaseTarget = onBaseTarget,
)
