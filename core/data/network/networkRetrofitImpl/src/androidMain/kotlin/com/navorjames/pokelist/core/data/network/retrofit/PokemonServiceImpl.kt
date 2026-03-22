package com.navorjames.pokelist.core.data.network.retrofit

import com.navorjames.pokelist.core.data.network.PokemonService
import com.navorjames.pokelist.core.data.network.data.Pokemon
import com.navorjames.pokelist.core.data.network.retrofit.PokeAPIModule.PATH_POKEMON_ID
import com.navorjames.pokelist.core.data.network.data.PokemonInfo
import com.navorjames.pokelist.core.data.network.data.paging.PagedResult
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface PokemonServiceImpl: PokemonService {
    @GET("pokemon/")
    override suspend fun getList(
        @Query("offset") offset: Int,
        @Query("limit") limit: Int
    ): PagedResult<Pokemon>

    @GET("pokemon/")
    override suspend fun getList(
        @QueryMap queries: Map<String, Int>
    ): PagedResult<Pokemon>

    @GET("pokemon/{pokemonId}")
    override suspend fun getPokemonInfo(
        @Path(PATH_POKEMON_ID) pokemonId: Int
    ): PokemonInfo
}