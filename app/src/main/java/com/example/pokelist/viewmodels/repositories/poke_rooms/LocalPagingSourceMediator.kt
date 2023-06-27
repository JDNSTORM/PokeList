package com.example.pokelist.viewmodels.repositories.poke_rooms

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.paging.util.getClippedRefreshKey
import com.example.pokelist.viewmodels.repositories.poke_api.PokeAPIModule
import com.example.pokelist.viewmodels.repositories.poke_api.entities.Pokemon

@OptIn(ExperimentalPagingApi::class)
class LocalPagingSourceMediator(
    private val loadList: suspend (Int, Int) -> List<Pokemon>,
    private val storeList: suspend (List<Pokemon>) -> Unit,
    private val clearTable: suspend () -> Unit
): RemoteMediator<Int, Pokemon>() {

    override suspend fun initialize(): InitializeAction = InitializeAction.LAUNCH_INITIAL_REFRESH

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, Pokemon>
    ): MediatorResult {
//        trackState(state)
        val offset = when(loadType){
            LoadType.REFRESH -> {
                clearTable.invoke()
                state.anchorPosition?.let { position ->
                    val closestPage = state.closestPageToPosition(position)
                    closestPage?.prevKey ?: closestPage?.nextKey
                } ?: PokeAPIModule.DEFAULT_OFFSET
            }
            LoadType.PREPEND -> {
                state.pages.firstOrNull() { it.data.isNotEmpty() }
                    ?.prevKey?.takeIf { it >= PokeAPIModule.DEFAULT_OFFSET }
                    ?: return MediatorResult.Success(true)
            }
            LoadType.APPEND -> { /** Returns the Default Offset if NULL on the assumption that Initial Refresh is Skipped */
                state.pages.lastOrNull { it.data.isNotEmpty() }?.nextKey
                    ?: PokeAPIModule.DEFAULT_OFFSET
            }
        }
        Log.d("Offset", offset.toString())

        val pageSize = state.config.pageSize.takeUnless { offset == PokeAPIModule.DEFAULT_OFFSET }
            ?: state.config.initialLoadSize
        return try {
            val pokeList = loadList(offset, pageSize)
            storeList(pokeList)
            MediatorResult.Success(pokeList.isEmpty())
        }catch (e: Exception){
            MediatorResult.Error(e)
        }
    }

    private fun getLastOffset(state: PagingState<Int, Pokemon>): Int? {
        val pageSize = state.config.pageSize
        val lastPage = state.pages.lastOrNull { it.data.isNotEmpty() }
        val lastItemsBefore = lastPage?.itemsBefore?.minus(Int.MIN_VALUE)
        val lastPageSize = lastPage?.data?.lastIndex?.plus(1)

        val estimatedTotalItems = lastItemsBefore?.plus(lastPageSize ?: 0)
        val remainder = estimatedTotalItems?.rem(pageSize)
        val initialPageNumber = estimatedTotalItems?.div(pageSize)
        val lastPageNumber = initialPageNumber?.let {
            var final = it
            if (remainder != null && remainder > 0){
                final++
            }
            final
        }

        Log.d("LastPageNumber", lastPageNumber.toString())
        return lastPageNumber?.minus(1)?.times(pageSize)
    }

    private fun getFirstOffset(state: PagingState<Int, Pokemon>): Int? {
        val pageSize = state.config.pageSize
        val firstPage = state.pages.firstOrNull { it.data.isNotEmpty() }
        val firstPageSize = firstPage?.data?.lastIndex?.plus(1)
        val firstItemsBefore = firstPage?.itemsBefore?.minus(Int.MIN_VALUE)

        val estimatedStartingItems = firstItemsBefore?.plus(firstPageSize ?: 0)
        val remainder = firstItemsBefore?.rem(pageSize)
        val prevPageNumber = firstItemsBefore?.div(pageSize)
        val firstPageNumber = prevPageNumber?.let {
            var final = it
            if (remainder != null && remainder > 0){
                final++
            }
            final
        }
        val offset = firstPageNumber?.times(pageSize)

        Log.d("FirstPageNumber", firstPageNumber.toString())
        Log.d("Offset", offset.toString())
        return offset
    }

    private fun getCurrentOffset(state: PagingState<Int, Pokemon>): Int? {
        val pageSize = state.config.pageSize
        val closestPage = state.anchorPosition?.let { state.closestPageToPosition(it) }
        val closestPageSize = closestPage?.data?.lastIndex?.plus(1)
        val closestItemsBefore = closestPage?.itemsBefore?.minus(Int.MIN_VALUE)

        val estimatedCurrentItems = closestItemsBefore?.plus(closestPageSize ?: 0)
        val remainder = estimatedCurrentItems?.rem(pageSize)
        val initialPageNumber = estimatedCurrentItems?.div(pageSize)
        val currentPageNumber = initialPageNumber?.let {
            var final = it
            if (remainder != null && remainder > 0){
                final++
            }
            final
        }

        Log.d("CurrentPageNumber", currentPageNumber.toString())
        return currentPageNumber?.minus(1)?.times(pageSize)
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