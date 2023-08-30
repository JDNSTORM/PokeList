package com.example.pokelist.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.pokelist.ui.models.InfoState
import com.example.pokelist.viewmodels.repositories.PokeAPIRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: PokeAPIRepository
): ViewModel() {
    val pokeListPagingData = repository.getPokeListStream()
//        .cachedIn(viewModelScope)
    private val _viewedPokemon: MutableStateFlow<InfoState> = MutableStateFlow(InfoState.Initial)
    private val viewedInfoState = MutableStateFlow<Int?>(null)
    init {
        viewModelScope.launch {
            viewedInfoState.collect{ id ->
                id?.let {
                    _viewedPokemon.emit(InfoState.Loading)
                    val state = repository.getInfoAsInfoState(id)
                    _viewedPokemon.emit(state)
                }
            }
        }
    }
    val viewedState: StateFlow<InfoState> = _viewedPokemon.asStateFlow()
    val getInfo: (Int) -> Unit = { id ->
        viewedInfoState.update { id }
    }

    init {
        viewModelScope.launch {
            repository.local.clearPokeList()
        }
    }
}