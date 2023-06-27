package com.example.pokelist.viewmodels.repositories.poke_api

import com.example.pokelist.viewmodels.repositories.poke_api.PokeAPIModule.PATH_POKEMON_ID
import com.example.pokelist.viewmodels.repositories.poke_api.entities.PokemonInfo
import com.example.pokelist.viewmodels.repositories.poke_api.entities.PokemonResult
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface PokemonService {
    @GET("pokemon/")
    suspend fun getList(
        @Query("offset") startInt: Int,
        @Query("limit") limit: Int
    ): PokemonResult

    @GET("pokemon/")
    suspend fun getList(
        @QueryMap queries: Map<String, Int>
    ): PokemonResult

    @GET("pokemon/{pokemonId}")
    suspend fun getPokemonInfo(
        @Path(PATH_POKEMON_ID) pokemonId: Int
    ): PokemonInfo
}