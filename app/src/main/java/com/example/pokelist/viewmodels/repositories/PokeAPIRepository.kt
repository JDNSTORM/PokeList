package com.example.pokelist.viewmodels.repositories

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.pokelist.ui.models.InfoState
import com.example.pokelist.viewmodels.repositories.poke_api.PokeAPIModule
import com.example.pokelist.viewmodels.repositories.poke_api.PokeListMediator
import com.example.pokelist.viewmodels.repositories.poke_api.RemoteDataSource
import com.example.pokelist.viewmodels.repositories.poke_api.entities.Pokemon
import com.example.pokelist.viewmodels.repositories.poke_rooms.LocalDataSource
import com.example.pokelist.viewmodels.repositories.poke_rooms.LocalPagingSource
import com.example.pokelist.viewmodels.repositories.poke_rooms.LocalPagingSourceFactory
import com.example.pokelist.viewmodels.repositories.poke_rooms.LocalPagingSourceMediator
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ViewModelScoped
class PokeAPIRepository @Inject constructor(
    val remote: RemoteDataSource,
    val local: LocalDataSource
) {

    @OptIn(ExperimentalPagingApi::class)
    fun getPokeListStream(): Flow<PagingData<Pokemon>> {
        val pagingConfig = PagingConfig(
            PokeAPIModule.DEFAULT_LIMIT,
            enablePlaceholders = true
        )
        val initialKey = PokeAPIModule.DEFAULT_OFFSET
        val remoteMediator = PokeListMediator(
            { offset: Int, limit: Int -> remote.getListDirectly(offset, limit) },
            { list: List<Pokemon> -> local.insertList(list) },
            { local.clearPokeList() }
        )
        val pagingSourceFactory = {
            local.getPokeListPagingSource()
        }

        val localPagingSourceFactory = { LocalPagingSource(
            { offset: Int, limit: Int -> local.getList(offset, limit) },
            { offset: Int, limit: Int -> remote.getListDirectly(offset, limit) },
            { list: List<Pokemon> -> local.insertList(list) }
        ) }

//        val localMediator = LocalPagingSourceMediator(
//            { offset: Int, limit: Int -> remote.getListDirectly(offset, limit) },
//            { list: List<Pokemon> -> local.insertList(list) },
//            { local.clearPokeList() },
//            { localPagingSourceFactory.invalidatePagingSource() }
//        )

        return Pager(
            pagingConfig,
            initialKey,
//            localMediator,
            remoteMediator,
//            pagingSourceFactory =  localPagingSourceFactory
            pagingSourceFactory
        ).flow
    }

    suspend fun getInfoAsInfoState(id: Int): InfoState {
        val localInfo = local.getPokemonInfoByID(id)
        return localInfo?.let {
            InfoState.Loaded(it)
        } ?: kotlin.run {
            try {
                val pokemon = remote.getInfoDirectly(id)
                local.insertPokemonInfo(pokemon)
                InfoState.Loaded(pokemon)
            }catch (e: Exception){
                Log.e("Pokemon API Call", e.message.toString())
                InfoState.Error(e, id)
            }
        }
    }
}