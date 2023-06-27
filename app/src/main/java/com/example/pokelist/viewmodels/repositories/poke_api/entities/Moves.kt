package com.example.pokelist.viewmodels.repositories.poke_api.entities


import com.example.pokelist.viewmodels.repositories.poke_api.entities.MoveInfo
import com.google.gson.annotations.SerializedName

data class Moves(
    @SerializedName("move")
    val moveInfo: MoveInfo = MoveInfo(),
)