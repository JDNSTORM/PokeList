package com.navorjames.pokelist.core.data.network.data.paging

/**
 * Represents a page of items with keys for navigating to previous and next pages.
 *
 * @param Key The type of the paging keys. Must be non-nullable.
 * @param Model The type of the items in the page.
 * @property key The key associated with the current page. Null if unknown.
 * @property prevKey The key for the previous page. Null if there is no previous page.
 * @property nextKey The key for the next page. Null if there is no next page.
 * @property items The list of items in the current page.
 */
data class PagedItems<Key: Any, Model>(
    val key: Key?,
    val prevKey: Key?,
    val nextKey: Key?,
    val items: List<Model>
)
