package org.napoleon.deadbalk.models

import org.napoleon.deadbalk.utils.findBy
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table
data class Player(
    @Id var id: Long? = null,
    val name: String,
    val position: String,
    val handedness: String,
    val playerType: String,
    val batterTarget: Int,
    val onBaseTarget: Int,
)

fun Player.toDto(): PlayerDto {

    val p = PlayerDto(
        name = name,
        position = position,
        handedness = handedness,
        playerType = (PlayerType::text findBy playerType) ?: PlayerType.VETERAN,
        batterTarget = batterTarget,
        onBaseTarget = onBaseTarget,
    )
    return p
}
