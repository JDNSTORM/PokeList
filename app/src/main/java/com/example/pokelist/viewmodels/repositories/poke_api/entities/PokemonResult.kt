package com.example.pokelist.viewmodels.repositories.poke_api.entities


import com.example.pokelist.viewmodels.repositories.poke_api.entities.Pokemon
import com.google.gson.annotations.SerializedName

data class PokemonResult(
    @SerializedName("count")
    val count: Int = 0,
    @SerializedName("next")
    val next: String? = null,
    @SerializedName("previous")
    val previous: String? = null,
    @SerializedName("results")
    val pokemonList: List<Pokemon> = listOf()
)