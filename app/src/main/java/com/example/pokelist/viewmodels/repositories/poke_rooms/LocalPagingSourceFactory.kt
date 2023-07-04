package com.example.pokelist.viewmodels.repositories.poke_rooms

import androidx.paging.PagingSource
import com.example.pokelist.viewmodels.repositories.poke_api.entities.Pokemon

class LocalPagingSourceFactory(
    private val getListLocally: suspend (Int, Int) -> List<Pokemon>,
    private val getListRemotely: suspend (Int, Int) -> List<Pokemon>,
    private val storeList: suspend (List<Pokemon>) -> Unit
): () -> PagingSource<Int, Pokemon> {
    private lateinit var pagingSource: LocalPagingSource

    override fun invoke(): PagingSource<Int, Pokemon> {
        pagingSource = LocalPagingSource(getListLocally, getListRemotely, storeList)
        return pagingSource
    }

    fun invalidatePagingSource(){
        pagingSource
        pagingSource.invalidate()
    }
}