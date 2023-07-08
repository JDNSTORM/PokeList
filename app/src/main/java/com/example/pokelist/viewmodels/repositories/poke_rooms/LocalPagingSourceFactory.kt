package com.example.pokelist.viewmodels.repositories.poke_rooms

import android.util.Log
import androidx.paging.PagingSource
import com.example.pokelist.viewmodels.repositories.poke_api.entities.Pokemon

@Deprecated("Paging doesn't behave as intended so this Factory is ineffective")
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
        Log.d("PagingSource", "Invalidated")
        pagingSource.invalidate()
    }
}