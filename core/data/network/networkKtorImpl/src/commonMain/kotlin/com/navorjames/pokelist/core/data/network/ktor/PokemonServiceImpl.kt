package com.navorjames.pokelist.core.data.network.ktor

import com.navorjames.pokelist.core.data.network.PokemonService
import com.navorjames.pokelist.core.data.network.data.PokemonInfo
import com.navorjames.pokelist.core.data.network.data.PokemonResult
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.appendPathSegments

internal class PokemonServiceImpl(
    private val client: HttpClient
): PokemonService {
    override suspend fun getList(offset: Int, limit: Int): PokemonResult {
        return client.get(BASE_URL){
            url {
                appendPathSegments(PATH_POKEMON_ID)
                parameters.append(QUERY_OFFSET, "$offset")
                parameters.append(QUERY_LIMIT, "$limit")
            }
        }.body()
    }

    override suspend fun getList(queries: Map<String, Int>): PokemonResult {
        return client.get(BASE_URL){
            url {
                appendPathSegments(PATH_POKEMON_ID)
                queries.forEach { (query, value) ->
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
        private const val BASE_URL = "https://pokeapi.co/api/v2/"
        private const val PATH_POKEMON_ID = "pokemon"
        private const val QUERY_OFFSET = "offset"
        private const val QUERY_LIMIT = "limit"
    }
}