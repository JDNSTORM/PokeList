package com.example.pokelist.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.example.pokelist.R
import com.example.pokelist.databinding.FragmentPokemonBinding
import com.example.pokelist.ui.models.InfoState
import com.example.pokelist.viewmodels.MainViewModel
import com.example.pokelist.viewmodels.repositories.poke_api.entities.PokemonInfo
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PokemonFragment : BaseFragment<FragmentPokemonBinding>() {
    override fun inflateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentPokemonBinding = FragmentPokemonBinding.inflate(
        inflater, container, false
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
        observeData(viewModel.viewedState, viewModel.getInfo)
    }

    private fun FragmentPokemonBinding.bind(info: PokemonInfo){
        pokemonId.text = info.id.toString()
        pokemonName.text = info.name.uppercase()
    }

    private fun FragmentPokemonBinding.loadState(state: InfoState, retry: (Int) -> Unit){
        val isInitial = state is InfoState.Initial
        val isLoading = state is InfoState.Loading
        val isLoaded = state is InfoState.Loaded
        val isError = state is InfoState.Error


        labelId.isVisible = isLoaded
        pokemonId.isVisible = isLoaded
        labelName.isVisible = isLoaded
        pokemonName.isVisible = isLoaded
        initialState.isVisible = isInitial
        progressBar.isVisible = isLoading
        layoutRetry.isVisible = isError

        when(state){
            is InfoState.Initial -> {}
            is InfoState.Loading -> {}
            is InfoState.Loaded -> binding.bind(state.info)
            is InfoState.Error -> {
                errorMessage.text = state.error.localizedMessage
                btnRetry.setOnClickListener { retry(state.pokemonID) }
            }
        }
    }

    private fun observeData(viewedState: StateFlow<InfoState>, retry: (Int) -> Unit) {
        lifecycleScope.launch {
            viewedState.flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .distinctUntilChanged().collect{binding.loadState(it, retry)
                }
        }
    }
}