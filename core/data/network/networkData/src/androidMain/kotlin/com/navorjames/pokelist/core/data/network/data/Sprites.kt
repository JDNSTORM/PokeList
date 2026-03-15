package com.navorjames.pokelist.core.data.network.data


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class Sprites(
    @SerializedName("front_default")
    @SerialName("front_default")
    val frontDefault: String = "",
    @SerializedName("front_shiny")
    @SerialName("front_shiny")
    val frontShiny: String = "",
): Parcelable