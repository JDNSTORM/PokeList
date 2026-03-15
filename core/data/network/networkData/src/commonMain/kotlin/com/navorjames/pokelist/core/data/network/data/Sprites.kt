package com.navorjames.pokelist.core.data.network.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Sprites(
    @SerialName("front_default")
    val frontDefault: String = "",
    @SerialName("front_shiny")
    val frontShiny: String = "",
)