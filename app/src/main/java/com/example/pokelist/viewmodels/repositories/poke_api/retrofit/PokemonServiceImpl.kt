package com.example.pokelist.viewmodels.repositories.poke_api.retrofit

import com.example.pokelist.viewmodels.repositories.poke_api.PokemonService
import com.example.pokelist.viewmodels.repositories.poke_api.retrofit.PokeAPIModule.PATH_POKEMON_ID
import com.navorjames.pokelist.core.data.network.data.PokemonInfo
import com.navorjames.pokelist.core.data.network.data.PokemonResult
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface PokemonServiceImpl: PokemonService {
    @GET("pokemon/")
    override suspend fun getList(
        @Query("offset") offset: Int,
        @Query("limit") limit: Int
    ): PokemonResult

    @GET("pokemon/")
    override suspend fun getList(
        @QueryMap queries: Map<String, Int>
    ): PokemonResult

    @GET("pokemon/{pokemonId}")
    override suspend fun getPokemonInfo(
        @Path(PATH_POKEMON_ID) pokemonId: Int
    ): PokemonInfo
}