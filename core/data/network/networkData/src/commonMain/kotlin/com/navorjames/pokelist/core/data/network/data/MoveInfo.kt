package com.navorjames.pokelist.core.data.network.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MoveInfo(
    @SerialName("name")
    val name: String = "",
    @SerialName("url")
    val url: String = ""
)