package com.example.pokelist.viewmodels.repositories.poke_api.entities


import com.google.gson.annotations.SerializedName
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Ability(
    @SerializedName("ability")
    @SerialName("ability")
    val abilityInfo: AbilityInfo = AbilityInfo(),
    @SerializedName("is_hidden")
    @SerialName("is_hidden")
    val isHidden: Boolean = false,
    @SerializedName("slot")
    @SerialName("slot")
    val slot: Int = 0
)