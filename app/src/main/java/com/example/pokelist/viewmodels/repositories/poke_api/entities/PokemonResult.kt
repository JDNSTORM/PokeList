package com.example.pokelist.viewmodels.repositories.poke_api.entities


import com.google.gson.annotations.SerializedName
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PokemonResult(
    @SerializedName("count")
    @SerialName("count")
    val count: Int = 0,
    @SerializedName("next")
    @SerialName("next")
    val next: String? = null,
    @SerializedName("previous")
    @SerialName("previous")
    val previous: String? = null,
    @SerializedName("results")
    @SerialName("results")
    val pokemonList: List<Pokemon> = emptyList()
)