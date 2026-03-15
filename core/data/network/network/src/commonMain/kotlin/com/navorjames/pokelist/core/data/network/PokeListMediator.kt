package com.navorjames.pokelist.core.data.network

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.navorjames.pokelist.core.data.network.data.Pokemon

@OptIn(ExperimentalPagingApi::class)
class PokeListMediator(
    private val loadList: suspend (Int, Int) -> List<Pokemon>,
    private val storeList: suspend (List<Pokemon>) -> Unit,
    private val clearTable: suspend () -> Unit
): RemoteMediator<Int, Pokemon>() {

    override suspend fun initialize(): InitializeAction = InitializeAction.SKIP_INITIAL_REFRESH

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, Pokemon>
    ): MediatorResult {
//        trackState(state)
        val pageSize = state.config.pageSize
        val offset = when(loadType){
            LoadType.REFRESH -> {
//                getCurrentOffset(state) ?:
                PokemonService.DEFAULT_OFFSET
            }
            LoadType.PREPEND -> {
                getFirstOffset(state)?.let {
                    if (it == PokemonService.DEFAULT_OFFSET) {
                        return MediatorResult.Success(true)
                    }
                    it
                }?.minus(pageSize)
                    ?.takeIf { it >= PokemonService.DEFAULT_OFFSET }
                    ?: return MediatorResult.Success(false)
            }
            LoadType.APPEND -> { /** Returns the Default Offset on the assumption that Initial Refresh is Skipped */
                getLastOffset(state)?.plus(pageSize)
                    ?: when(initialize()){
                        InitializeAction.LAUNCH_INITIAL_REFRESH -> return MediatorResult.Success(false)
                        InitializeAction.SKIP_INITIAL_REFRESH -> PokemonService.DEFAULT_OFFSET
                    }
            }
        }

        return try {
            val pokeList = loadList(offset, pageSize)
            if (loadType == LoadType.REFRESH) clearTable()
            storeList(pokeList)
            MediatorResult.Success(pokeList.isEmpty())
        }catch (e: Exception){
            MediatorResult.Error(e)
        }
    }

    private fun getLastOffset(state: PagingState<Int, Pokemon>): Int? {
        val pageSize = state.config.pageSize
        val lastPage = state.pages.lastOrNull { it.data.isNotEmpty() }
        val lastItemsBefore = lastPage?.itemsBefore
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

        return lastPageNumber?.minus(1)?.times(pageSize)
    }

    private fun getFirstOffset(state: PagingState<Int, Pokemon>): Int? {
        val pageSize = state.config.pageSize
        val firstPage = state.pages.firstOrNull { it.data.isNotEmpty() }
        val firstPageSize = firstPage?.data?.lastIndex?.plus(1)
        val firstItemsBefore = firstPage?.itemsBefore

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

        return offset
    }

    private fun getCurrentOffset(state: PagingState<Int, Pokemon>): Int? {
        val pageSize = state.config.pageSize
        val closestPage = state.anchorPosition?.let { state.closestPageToPosition(it) }
        val closestPageSize = closestPage?.data?.lastIndex?.plus(1)
        val closestItemsBefore = closestPage?.itemsBefore

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

        return currentPageNumber?.minus(1)?.times(pageSize)
    }
}