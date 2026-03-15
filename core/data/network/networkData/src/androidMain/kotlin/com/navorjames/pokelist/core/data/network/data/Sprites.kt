package com.navorjames.pokelist.core.data.network.data


import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class Sprites(
    @SerialName("front_default")
    val frontDefault: String = "",
    @SerialName("front_shiny")
    val frontShiny: String = "",
): Parcelable