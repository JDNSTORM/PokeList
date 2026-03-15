package com.example.pokelist.viewmodels.repositories.poke_api

import com.navorjames.pokelist.core.data.network.data.PokemonInfo
import com.navorjames.pokelist.core.data.network.data.PokemonResult

interface PokemonService {
    suspend fun getList(offset: Int, limit: Int): PokemonResult

    suspend fun getList(queries: Map<String, Int>): PokemonResult

    suspend fun getPokemonInfo(pokemonId: Int): PokemonInfo
}