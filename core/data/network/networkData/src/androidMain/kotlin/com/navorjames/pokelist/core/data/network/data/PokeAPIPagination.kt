package com.navorjames.pokelist.core.data.network.data

import com.google.gson.annotations.SerializedName

open class PokeAPIPagination(
    @SerializedName("count")
    val count: Int = 0,
    @SerializedName("next")
    val next: String? = null,
    @SerializedName("previous")
    val previous: String? = null,
)