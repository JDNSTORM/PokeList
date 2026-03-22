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
) {
    companion object {
        /**
         * Maps the items in this [PagedItems] to a list of remote keys, calculating a unique position
         * for each item based on the current page's offset key.
         *
         * @param T The type of the items in the page.
         * @param Keys The type of remote keys to be created, implementing [PagingRemoteKeys] with an [Int] key.
         * @param createRemoteKeys A lambda that creates a remote key instance for a given item and its calculated position.
         */
        inline fun <T: Any, Keys: PagingRemoteKeys<Int>> PagedItems<Int, T>.offsetBasedRemoteKeys(
            createRemoteKeys: (T, position: Int) -> Keys
        ): List<Keys> {
            val offset = key ?: 0
            return items.mapIndexed { index, item ->
                val position = offset + index
                createRemoteKeys(item, position)
            }
        }

        /**
         * Maps the items in this [PagedItems] to a list of remote keys, calculating a unique position
         * for each item based on a provided starting position.
         *
         * **Usage**: This [PagedItems] uses *Page Numbers* as keys. A query will be needed to get the Min or Max Position based on the [prevKey] or [nextKey]
         *
         * @param T The type of the items in the page.
         * @param Keys The type of remote keys to be created, implementing [PagingRemoteKeys] with an [Int] key.
         * @param startingPosition The base index to start counting from for this set of items.
         * @param createRemoteKeys A lambda that creates a remote key instance for a given item and its calculated position.
         */
        inline fun <T: Any, Keys: PagingRemoteKeys<Int>> PagedItems<Int, T>.pageBasedRemoteKeys(
            startingPosition: Int,
            createRemoteKeys: (T, position: Int) -> Keys
        ): List<Keys> = items.mapIndexed { index, item ->
            val position = startingPosition + index
            createRemoteKeys(item, position)
        }
    }
}
