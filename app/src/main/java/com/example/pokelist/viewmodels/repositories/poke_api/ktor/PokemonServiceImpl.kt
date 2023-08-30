package com.example.pokelist.viewmodels.repositories.poke_api.ktor

import com.example.pokelist.viewmodels.repositories.poke_api.PokemonService
import com.example.pokelist.viewmodels.repositories.poke_api.entities.PokemonInfo
import com.example.pokelist.viewmodels.repositories.poke_api.entities.PokemonResult
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.appendPathSegments

class PokemonServiceImpl(
    private val client: HttpClient
): PokemonService {
    override suspend fun getList(startInt: Int, limit: Int): PokemonResult {
        return client.get(BASE_URL){
            url {
                appendPathSegments(PATH_POKEMON_ID)
                parameters.append(QUERY_OFFSET, "$startInt")
                parameters.append(QUERY_LIMIT, "$limit")
            }
        }.body()
    }

    override suspend fun getList(queries: Map<String, Int>): PokemonResult {
        return client.get(BASE_URL){
            url {
                appendPathSegments(PATH_POKEMON_ID)
                queries.forEach { query, value ->
                    parameters.append(query, "$value")
                }
            }
        }.body()
    }

    override suspend fun getPokemonInfo(pokemonId: Int): PokemonInfo {
        return client.get(BASE_URL){
            url {
                appendPathSegments(PATH_POKEMON_ID)
                appendPathSegments("$pokemonId")
            }
        }.body()
    }

    companion object{
        private const val BASE_URL = "https://pokeapi.co/api/v2"
        private const val PATH_POKEMON_ID = "pokemon"
        private const val QUERY_OFFSET = "offset"
        private const val QUERY_LIMIT = "limit"
    }
}