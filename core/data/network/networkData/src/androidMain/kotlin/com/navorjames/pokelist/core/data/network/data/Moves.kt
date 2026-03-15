package com.navorjames.pokelist.core.data.network.data


import com.google.gson.annotations.SerializedName
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Moves(
    @SerializedName("move")
    @SerialName("move")
    val moveInfo: MoveInfo = MoveInfo(),
)