package com.example.pokelist.ui.models

import com.example.pokelist.viewmodels.repositories.poke_api.entities.PokemonInfo

sealed interface InfoState{
    object Initial : InfoState
    object Loading: InfoState
    data class Loaded(val info: PokemonInfo): InfoState
    data class Error(val error: Throwable, val pokemonID: Int): InfoState
}