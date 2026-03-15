package com.navorjames.pokelist.core.data.network.data


import com.google.gson.annotations.SerializedName
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AbilityInfo(
    @SerializedName("name")
    @SerialName("name")
    val name: String = "",
    @SerializedName("url")
    @SerialName("url")
    val url: String = ""
)