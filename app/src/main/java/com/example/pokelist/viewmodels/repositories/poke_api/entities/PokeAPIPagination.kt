package com.example.pokelist.viewmodels.repositories.poke_api.entities

import com.google.gson.annotations.SerializedName

open class PokeAPIPagination(
    @SerializedName("count")
    val count: Int = 0,
    @SerializedName("next")
    val next: String? = null,
    @SerializedName("previous")
    val previous: String? = null,
)