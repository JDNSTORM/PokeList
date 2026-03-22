package com.example.pokelist.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokelist.ui.models.InfoState
import com.example.pokelist.viewmodels.repositories.PokeAPIRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
class MainViewModel(
    private val repository: PokeAPIRepository
): ViewModel() {
    val pokeListPagingData = repository.getPokeListStream()
    private val viewedPokemonIdState = MutableStateFlow<Int?>(null)
    val viewedInfoState: StateFlow<InfoState> = viewedPokemonIdState.flatMapLatest {
        it ?: return@flatMapLatest flowOf(InfoState.Initial)
        repository.readInfoAsState(it)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = InfoState.Initial
    )
    val getInfo: (Int) -> Unit = { id ->
        viewedPokemonIdState.update { id }
    }
}