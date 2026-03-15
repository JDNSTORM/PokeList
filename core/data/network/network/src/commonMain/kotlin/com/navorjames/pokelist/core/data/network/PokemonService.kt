package com.navorjames.pokelist.core.data.network

import com.navorjames.pokelist.core.data.network.data.PokemonInfo
import com.navorjames.pokelist.core.data.network.data.PokemonResult

interface PokemonService {
    suspend fun getList(offset: Int, limit: Int): PokemonResult

    suspend fun getList(queries: Map<String, Int>): PokemonResult

    suspend fun getPokemonInfo(pokemonId: Int): PokemonInfo

    companion object {
        const val BASE_URL = "https://pokeapi.co/api/v2/"
        const val QUERY_OFFSET = "offset"
        const val DEFAULT_OFFSET = 0
        const val QUERY_LIMIT = "limit"
        const val DEFAULT_LIMIT = 20
        const val PATH_POKEMON_ID = "pokemonId"
    }
}