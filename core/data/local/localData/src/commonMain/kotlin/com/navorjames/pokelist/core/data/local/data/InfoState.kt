package com.navorjames.pokelist.core.data.local.data

import com.navorjames.pokelist.core.data.network.data.PokemonInfo

sealed interface InfoState{
    object Initial : InfoState
    object Loading: InfoState
    data class Loaded(val info: PokemonInfo): InfoState
    data class Error(val error: Throwable, val pokemonID: Int): InfoState
}