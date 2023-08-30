package com.example.pokelist.viewmodels.repositories.poke_api.entities


import com.example.pokelist.viewmodels.repositories.poke_api.entities.MoveInfo
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Moves(
    @SerializedName("move")
    @SerialName("move")
    val moveInfo: MoveInfo = MoveInfo(),
)