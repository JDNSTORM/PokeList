package com.example.pokelist.viewmodels.repositories.poke_api.entities


import com.google.gson.annotations.SerializedName

data class Ability(
    @SerializedName("ability")
    val abilityInfo: AbilityInfo = AbilityInfo(),
    @SerializedName("is_hidden")
    val isHidden: Boolean = false,
    @SerializedName("slot")
    val slot: Int = 0
)