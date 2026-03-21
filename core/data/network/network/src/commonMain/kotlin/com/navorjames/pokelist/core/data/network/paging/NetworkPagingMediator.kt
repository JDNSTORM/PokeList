package com.navorjames.pokelist.core.data.network.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.navorjames.pokelist.core.data.network.data.paging.PagedItems
import com.navorjames.pokelist.core.data.network.data.paging.PagedResult
import com.navorjames.pokelist.core.data.network.data.paging.PagingRemoteKeys
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * A [RemoteMediator] implementation designed to handle page-based pagination by synchronizing
 * a remote network data source with a local persistent data store.
 *
 * This mediator orchestrates the data flow based on the [LoadType]:
 * - [LoadType.REFRESH]: Fetches the [initialOffset] and triggers a local data reset.
 * - [LoadType.PREPEND]: Retrieves the previous page key via [getRemoteKeys]; if null, prepending is disabled.
 * - [LoadType.APPEND]: Retrieves the next page key via [getRemoteKeys]; if null, appending is disabled.
 *
 * @param UiModel The type of data presented in the UI layer (domain model).
 * @param Model The type of data retrieved from the network and stored in the database (DTO/Entity).
 * @param initialOffset The starting offset for pagination (defaults to 0).
 * @param getRemoteKeys A suspend function to retrieve [PagingRemoteKeys] for a specific [UiModel] from the local database.
 *                      Returns a [PagingRemoteKeys] object or `null` if no keys are found.
 *                      **Note**: The `RemoteMediator` may `load` before stored items are emitted.
 *                      Make sure to check if this exists to `endOfPaginationReached`.
 * @param fetchList A suspend function that executes the network request, returning [PagedData] containing items and pagination metadata.
 * @param storeItems A suspend function responsible for persisting [PagedItems] (data and keys) to the local database.
 * @param dispatcher The [CoroutineDispatcher] to use for executing the network and database operations. Defaults to [Dispatchers.IO].
 */
@OptIn(ExperimentalPagingApi::class)
class NetworkPagingMediator<UiModel : Any, Model : Any>(
    private val initialOffset: Int = 0,
    private val getRemoteKeys: suspend (UiModel) -> PagingRemoteKeys<Int>?,
    private val fetchList: suspend (pageSize: Int, offset: Int) -> PagedResult<Model>,
    private val storeItems: suspend (PagedItems<Int, Model>, clearData: Boolean) -> Unit,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
) : RemoteMediator<Int, UiModel>() {
    override suspend fun initialize(): InitializeAction = InitializeAction.SKIP_INITIAL_REFRESH

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, UiModel>
    ): MediatorResult = withContext(dispatcher) {
        val offset = when (loadType) {
            LoadType.REFRESH -> {
                state.anchorPosition?.let { anchorPosition ->
                    val model = state.closestItemToPosition(anchorPosition) ?: return@let null
                    getRemoteKeys(model)?.currentKey
                }?: initialOffset
            }
            LoadType.PREPEND -> {
                val remoteKeys = state.firstItemOrNull()?.let { model ->
                    getRemoteKeys(model)
                }
                remoteKeys?.prevKey
                    ?: return@withContext MediatorResult.Success(
                        remoteKeys != null
                    )
            }
            LoadType.APPEND -> {
                val remoteKeys = state.lastItemOrNull()?.let { model ->
                    getRemoteKeys(model)
                }
                remoteKeys?.nextKey
                    ?: return@withContext MediatorResult.Success(
                        remoteKeys != null
                    )
            }
        }

        val isRefresh = loadType == LoadType.REFRESH
        val pageSize = if (isRefresh) state.config.initialLoadSize else state.config.pageSize

        try {
            val result = fetchList(pageSize, offset)
            val prevKey = offset
                .takeIf { result.previousUrl != null }
                ?.minus(pageSize)
            val nextKey = offset
                .takeIf { result.nextUrl != null }
                ?.plus(pageSize)

            storeItems(
                PagedItems(
                    key = offset,
                    prevKey = prevKey,
                    nextKey = nextKey,
                    items = result.items
                ),
                isRefresh
            )
            MediatorResult.Success(nextKey == null)
        }
        catch(t: Throwable) {
            t.printStackTrace()
            MediatorResult.Error(t)
        }
    }

}