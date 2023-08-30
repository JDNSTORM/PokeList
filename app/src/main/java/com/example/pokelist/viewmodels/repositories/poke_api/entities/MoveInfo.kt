package com.example.pokelist.viewmodels.repositories.poke_api.entities


import com.google.gson.annotations.SerializedName
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MoveInfo(
    @SerializedName("name")
    @SerialName("name")
    val name: String = "",
    @SerializedName("url")
    @SerialName("url")
    val url: String = ""
)