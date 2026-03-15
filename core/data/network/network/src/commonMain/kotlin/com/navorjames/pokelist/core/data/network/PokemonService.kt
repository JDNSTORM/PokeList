package com.navorjames.pokelist.core.data.network

import com.navorjames.pokelist.core.data.network.data.PokemonInfo
import com.navorjames.pokelist.core.data.network.data.PokemonResult

interface PokemonService {
    suspend fun getList(offset: Int, limit: Int): PokemonResult

    suspend fun getList(queries: Map<String, Int>): PokemonResult

    suspend fun getPokemonInfo(pokemonId: Int): PokemonInfo

    companion object {
        const val DEFAULT_OFFSET = 0
        const val BASE_URL = "https://pokeapi.co/api/v2/"
    }
}