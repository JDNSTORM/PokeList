package com.example.pokelist.viewmodels.repositories.poke_rooms

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.pokelist.viewmodels.repositories.poke_api.PokeAPIModule
import com.example.pokelist.viewmodels.repositories.poke_api.entities.Pokemon

class LocalPagingSource(
    private val getListLocally: suspend (Int, Int) -> List<Pokemon>,
    private val getListRemotely: suspend (Int, Int) -> List<Pokemon>,
    private val storeList: suspend (List<Pokemon>) -> Unit
    ): PagingSource<Int, Pokemon>() {
    override fun getRefreshKey(state: PagingState<Int, Pokemon>): Int? {
        val refreshKey = state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
        Log.d("RefreshKey", refreshKey.toString())
        return refreshKey
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Pokemon> {
        val position = params.key?.takeIf { it > PokeAPIModule.DEFAULT_OFFSET } ?: PokeAPIModule.DEFAULT_OFFSET
        Log.d("Anchor", position.toString())
        return try {
            val localList = getListLocally(position, params.loadSize)
//                .takeUnless { it.isEmpty() }
            val pokeList = localList
                ?: kotlin.run {
                val remoteList = getListRemotely(position, params.loadSize)
                storeList(remoteList)
                remoteList
            }
            val prevKey = params.key?.minus(params.loadSize)?.takeIf { it > PokeAPIModule.DEFAULT_OFFSET }
            val nextKey = if (pokeList.isEmpty()){
                null
            }else{
                position + params.loadSize
            }
            LoadResult.Page(pokeList, prevKey, nextKey)
        }catch (e: Exception){
            LoadResult.Error(e)
        }
    }
}