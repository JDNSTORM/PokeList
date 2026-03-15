package com.navorjames.pokelist.core.data.network.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Moves(
    @SerialName("move")
    val moveInfo: MoveInfo = MoveInfo(),
)