package com.example.pokelist.viewmodels.repositories.poke_api

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.navorjames.pokelist.core.data.network.data.Pokemon
import com.navorjames.pokelist.core.data.network.data.PokemonInfo
import com.navorjames.pokelist.core.data.network.data.PokemonResult
import com.example.pokelist.viewmodels.repositories.poke_api.retrofit.PokeAPIModule
import kotlinx.coroutines.flow.Flow

class RemoteDataSource(private val service: PokemonService) {
    suspend fun getList(offset: Int, limit: Int): PokemonResult{
        return service.getList(offset, limit)
    }

    suspend fun getList(queries: Map<String, Int>): PokemonResult{
        return service.getList(queries)
    }

    suspend fun getInfo(id: Int): PokemonInfo {
        return service.getPokemonInfo(id)
    }

    suspend fun getInfoDirectly(id: Int): PokemonInfo = service.getPokemonInfo(id)

    fun getPokeListStream(): Flow<PagingData<Pokemon>> {
        return Pager(
            PagingConfig(
                PokeAPIModule.DEFAULT_LIMIT,
                enablePlaceholders = false
            ),
            PokeAPIModule.DEFAULT_OFFSET,
            pagingSourceFactory = {
                PokeListPagingSource{ offset: Int, limit: Int ->
                    service.getList(offset, limit).pokemonList
                }
            }
        ).flow
    }

    suspend fun getListDirectly(offset: Int, limit: Int): List<Pokemon> = service.getList(offset, limit).pokemonList
}