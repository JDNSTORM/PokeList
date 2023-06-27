package com.example.pokelist.viewmodels.repositories.poke_api.entities


import com.google.gson.annotations.SerializedName

data class AbilityInfo(
    @SerializedName("name")
    val name: String = "",
    @SerializedName("url")
    val url: String = ""
)