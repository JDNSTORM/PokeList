package com.example.pokelist.viewmodels.repositories.poke_rooms

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.room.paging.util.getClippedRefreshKey
import com.example.pokelist.viewmodels.repositories.poke_api.PokeAPIModule
import com.example.pokelist.viewmodels.repositories.poke_api.entities.Pokemon

class LocalPagingSource(
    private val getListLocally: suspend (Int, Int) -> List<Pokemon>,
    private val getListRemotely: suspend (Int, Int) -> List<Pokemon>,
    private val storeList: suspend (List<Pokemon>) -> Unit
    ): PagingSource<Int, Pokemon>() {
    override fun getRefreshKey(state: PagingState<Int, Pokemon>): Int? {
        val refreshKey = state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey
                ?: state.closestPageToPosition(it)?.nextKey
        }
        return refreshKey
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Pokemon> {
        val position = params.key?.takeIf { it > PokeAPIModule.DEFAULT_OFFSET } ?: PokeAPIModule.DEFAULT_OFFSET
        Log.d("Anchor", position.toString())
        return try {
            val pokeList = getListLocally(position, params.loadSize).takeUnless { it.isEmpty() }
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
            val result = LoadResult.Page(pokeList, prevKey, nextKey, prevKey ?: 0)
            Log.d("Result", "${result.prevKey}, ${result.nextKey}")
            result
        }catch (e: Exception){
            LoadResult.Error(e)
        }
    }

    private fun trackState(state: PagingState<Int, Pokemon>){
        val position = state.anchorPosition
        val closestItem = position?.let { state.closestItemToPosition(it) }
        val closestPage = position?.let { state.closestPageToPosition(it) }
        val closestPageSize = closestPage?.data?.lastIndex?.plus(1)
        val closestItemsBefore = closestPage?.itemsBefore
        val closestItemsAfter = closestPage?.itemsAfter
        val closestPrevKey = closestPage?.prevKey
        val closestNextKey = closestPage?.nextKey
        val pageSize = state.config.pageSize //*
        val initialPageSize = state.config.initialLoadSize //*
        val refreshKey = state.getClippedRefreshKey()
        val pages = state.pages.size
        val pageCount = state.pages.count { it.data.isNotEmpty() }
        val lastIndex = state.pages.lastIndex
        val firstItem = state.firstItemOrNull() //**
        val firstPage = state.pages.firstOrNull { it.data.isNotEmpty() }
        val firstPageSize = firstPage?.data?.lastIndex?.plus(1)
        val firstRepo = firstPage?.data?.firstOrNull()
        val firstItemsBefore = firstPage?.itemsBefore
        val firstItemsAfter = firstPage?.itemsAfter
        val firstPrevKey = firstPage?.prevKey
        val firstNextKey = firstPage?.nextKey
        val lastItem = state.lastItemOrNull() //**
        val lastPage = state.pages.lastOrNull { it.data.isNotEmpty() }
        val lastPageSize = lastPage?.data?.lastIndex?.plus(1)
        val lastRepo = lastPage?.data?.lastOrNull()
        val lastItemsBefore = lastPage?.itemsBefore
        val lastItemsAfter = lastPage?.itemsAfter
        val lastPrevKey = lastPage?.prevKey
        val lastNextKey = lastPage?.nextKey

        val veryLastPage = state.pages.lastOrNull()

        val estimatedTotalPages = lastItemsBefore?.plus(lastPageSize ?: 0)
        val remainder = estimatedTotalPages?.rem(pageSize)
        val halfPage = pageSize / 2
        val initialPageNumber = estimatedTotalPages?.div(pageSize)
        val finalPageNumber = initialPageNumber?.let {
            var final = it
            if (remainder != null && remainder > 0){
                final += 1
            }
            final
        }

        Log.d("Position", position.toString())
        Log.d("ClosestItem", closestItem.toString())
        Log.d("ClosestPage", closestPage.toString())
        Log.d("ClosestPageSize", closestPageSize.toString())
        Log.d("ClosestItemsBefore", closestItemsBefore.toString())
        Log.d("ClosestItemsAfter", closestItemsAfter.toString())
        Log.d("ClosestPrevKey", closestPrevKey.toString())
        Log.d("ClosestNextKey", closestNextKey.toString())
        Log.d("PageSize", pageSize.toString())
        Log.d("InitialPageSize", initialPageSize.toString())
        Log.d("RefreshKey", refreshKey.toString())
        Log.d("Pages", pages.toString())
        Log.d("PageCount", pageCount.toString())
        Log.d("LastIndex", lastIndex.toString())
        Log.d("FirstItem", firstItem.toString())
        Log.d("FirstPage", firstPage.toString())
        Log.d("FirstPageSize", firstPageSize.toString())
        Log.d("FirstRepo", firstRepo.toString())
        Log.d("FirstItemsBefore", firstItemsBefore.toString())
        Log.d("FirstItemsAfter", firstItemsAfter.toString())
        Log.d("FirstPrevKey", firstPrevKey.toString())
        Log.d("FirstNextKey", firstNextKey.toString())
        Log.d("LastItem", lastItem.toString())
        Log.d("LastPage", lastPage.toString())
        Log.d("LastPageSize", lastPageSize.toString())
        Log.d("LastRepo", lastRepo.toString())
        Log.d("LastItemsBefore", lastItemsBefore.toString())
        Log.d("LastItemsAfter", lastItemsAfter.toString())
        Log.d("LastPrevKey", lastPrevKey.toString())
        Log.d("LastNextKey", lastNextKey.toString())
        Log.d("VeryLastPage", veryLastPage.toString())

        Log.d("EstimatedTotalPages", estimatedTotalPages.toString())
        Log.d("Remainder", remainder.toString())
        Log.d("HalfPage", halfPage.toString())
        Log.d("InitialPageNumber", initialPageNumber.toString())
        Log.d("FinalPageNumber", finalPageNumber.toString())
    }
}