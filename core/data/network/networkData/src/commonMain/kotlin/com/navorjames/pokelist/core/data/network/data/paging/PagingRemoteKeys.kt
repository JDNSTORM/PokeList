package com.navorjames.pokelist.core.data.network.data.paging

/**
 * An interface for an `Entity` representing the remote keys for a paginated data set.
 * Remote keys are used to keep track of the previous and next pages of data
 * that can be fetched from a remote source (e.g., an API).
 *
 * This interface is typically used within a database table that stores remote keys
 * associated with items in a paginated list.  Each item in the list would likely
 * have a corresponding entry in the remote keys table, allowing efficient retrieval
 * of the previous and next pages of data.
 *
 * @param Key The type of the pagination key (e.g., [Int], [String], or a cursor object).
 * @property currentKey The key identifying the current page or the item itself.
 * @property prevKey The key used to fetch the previous page of data. Null if no previous page exists.
 * @property nextKey The key used to fetch the next page of data. Null if no further pages are available.
 * @property position The ordinal position or index used to maintain the sorted order of the paginated items.
 */
interface PagingRemoteKeys<Key: Any>{
    val currentKey: Key?
    val prevKey: Key?
    val nextKey: Key?
    val position: Int
}