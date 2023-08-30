package com.example.pokelist.viewmodels.repositories.poke_api.ktor

import com.example.pokelist.viewmodels.repositories.poke_api.PokemonService
import com.example.pokelist.viewmodels.repositories.poke_api.entities.PokemonInfo
import com.example.pokelist.viewmodels.repositories.poke_api.entities.PokemonResult
import io.ktor.client.HttpClient

class PokemonServiceImpl(
    private val client: HttpClient
): PokemonService {
    override suspend fun getList(startInt: Int, limit: Int): PokemonResult {
        TODO("Not yet implemented")
    }

    override suspend fun getList(queries: Map<String, Int>): PokemonResult {
        TODO("Not yet implemented")
    }

    override suspend fun getPokemonInfo(pokemonId: Int): PokemonInfo {
        TODO("Not yet implemented")
    }

    companion object{
        private const val BASE_URL = "https://pokeapi.co/api/v2/"
        private const val PATH_POKEMON_ID = "pokemon/"
        private const val QUERY_OFFSET = "offset"
        private const val QUERY_LIMIT = "limit"
    }
}