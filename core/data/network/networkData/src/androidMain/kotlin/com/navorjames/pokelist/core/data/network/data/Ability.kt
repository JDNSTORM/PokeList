package com.navorjames.pokelist.core.data.network.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Ability(
    @SerialName("ability")
    val abilityInfo: AbilityInfo = AbilityInfo(),
    @SerialName("is_hidden")
    val isHidden: Boolean = false,
    @SerialName("slot")
    val slot: Int = 0
)