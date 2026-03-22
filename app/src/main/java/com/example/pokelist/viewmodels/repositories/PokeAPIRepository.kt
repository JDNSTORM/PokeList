package com.example.pokelist.viewmodels.repositories

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.pokelist.ui.models.InfoState
import com.navorjames.pokelist.core.data.local.LocalDataSource
import com.navorjames.pokelist.core.data.network.PokeListMediator
import com.navorjames.pokelist.core.data.network.PokemonService
import com.navorjames.pokelist.core.data.network.RemoteDataSource
import com.navorjames.pokelist.core.data.network.data.Pokemon
import com.navorjames.pokelist.core.data.network.paging.NetworkPagingMediator
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class PokeAPIRepository(
    val remote: RemoteDataSource,
    val local: LocalDataSource
) {

    @OptIn(ExperimentalPagingApi::class)
    fun getPokeListStream(): Flow<PagingData<Pokemon>> {
        val pagingConfig = PagingConfig(
            PokemonService.DEFAULT_LIMIT,
            enablePlaceholders = true
        )
        val initialKey = PokemonService.DEFAULT_OFFSET
        val remoteMediator = NetworkPagingMediator<Pokemon, Pokemon>(
            initialOffset = initialKey,
            getRemoteKeys = {
                local.getRemoteKeys(it.id)
            },
            fetchList = { pageSize, offset ->
                remote.getList(offset, pageSize)
            },
            storeItems = { items, clearData ->
                local.insertPagedPokemons(items, clearData)
            },
            shouldLaunchInitialRefresh = {
                !local.hasCachedPokemons()
            }
        )
        val pagingSourceFactory = {
            local.getPokemonPagingSource()
        }

        return Pager(
            pagingConfig,
            initialKey,
            remoteMediator,
            pagingSourceFactory
        ).flow
    }

    suspend fun getInfoAsInfoState(id: Int): InfoState {
        val localInfo = local.getPokemonInfoByID(id)
        return localInfo?.let {
            InfoState.Loaded(it)
        } ?: run {
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

    fun readInfoAsState(id: Int): Flow<InfoState> = channelFlow {
        send(InfoState.Loading)

        launch {
            local.readPokemonInfoByID(id).collectLatest {
                if (it == null) return@collectLatest
                send(InfoState.Loaded((it)))
            }
        }

        if (local.readPokemonInfoByID(id).first() != null) return@channelFlow
        try {
            val pokemon = remote.getInfoDirectly(id)
            local.insertPokemonInfo(pokemon)
        }catch (e: Exception){
            Log.e("Pokemon API Call", e.message.toString())
            send(
                InfoState.Error(e, id)
            )
        }
    }
}