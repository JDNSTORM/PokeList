package com.navorjames.pokelist.core.data.network

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.navorjames.pokelist.core.data.network.data.Pokemon
import com.navorjames.pokelist.core.data.network.data.PokemonInfo
import com.navorjames.pokelist.core.data.network.data.PokemonResult
import com.navorjames.pokelist.core.data.network.data.paging.PagedResult
import kotlinx.coroutines.flow.Flow

class RemoteDataSource(private val service: PokemonService) {
    suspend fun getList(offset: Int, limit: Int): PagedResult<Pokemon> =
        service.getList(offset, limit)

    suspend fun getList(queries: Map<String, Int>): PagedResult<Pokemon> = service.getList(queries)

    suspend fun getInfo(id: Int): PokemonInfo {
        return service.getPokemonInfo(id)
    }

    fun getPokeListStream(): Flow<PagingData<Pokemon>> {
        return Pager(
            PagingConfig(
                PokemonService.DEFAULT_LIMIT,
                enablePlaceholders = false
            ),
            PokemonService.DEFAULT_OFFSET,
            pagingSourceFactory = {
                PokeListPagingSource { offset: Int, limit: Int ->
                    service.getList(offset, limit).items
                }
            }
        ).flow
    }
}