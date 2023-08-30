package com.example.pokelist.viewmodels.repositories.poke_api

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.pokelist.viewmodels.repositories.poke_api.entities.Pokemon
import com.example.pokelist.viewmodels.repositories.poke_api.retrofit.PokeAPIModule
import retrofit2.HttpException
import java.io.IOException

class PokeListPagingSource(
    private val getList: suspend (Int, Int) -> List<Pokemon>
): PagingSource<Int, Pokemon>() {
    override fun getRefreshKey(state: PagingState<Int, Pokemon>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Pokemon> {
        val position = params.key?.takeIf { it > PokeAPIModule.DEFAULT_OFFSET } ?: PokeAPIModule.DEFAULT_OFFSET
        try {
            val pokeList = getList(position, params.loadSize)
            val prevKey = params.key?.minus(params.loadSize)?.takeIf { it > PokeAPIModule.DEFAULT_OFFSET }
            val nextKey = if (pokeList.isEmpty()){
                null
            }else{
                position + params.loadSize
            }
            return LoadResult.Page(
                pokeList,
                prevKey,
                nextKey
            )
        }catch (e: IOException){
            return LoadResult.Error(e)
        }catch (e: HttpException){
            return LoadResult.Error(e)
        }
    }
}