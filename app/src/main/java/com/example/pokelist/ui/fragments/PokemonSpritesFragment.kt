package com.example.pokelist.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.pokelist.R
import com.example.pokelist.databinding.FragmentPokemonSpritesBinding
import com.example.pokelist.ui.models.InfoState
import com.example.pokelist.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import ph.projects.navor_jamesdave.apis.versions.with_gson.pokeapi_entities.Sprites

@AndroidEntryPoint
class PokemonSpritesFragment : BaseFragment<FragmentPokemonSpritesBinding>() {
    override fun inflateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentPokemonSpritesBinding = FragmentPokemonSpritesBinding.inflate(
        inflater, container, false
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
        observeData(viewModel.viewedState, viewModel.getInfo)
    }

    private fun FragmentPokemonSpritesBinding.bind(sprites: Sprites){
        pokemonDefault.load(R.drawable.egg, sprites.frontDefault)
        pokemonShiny.load(R.drawable.egg_shiny, sprites.frontShiny)
    }

    private fun ImageView.load(@DrawableRes placeholder: Int, url: String){
        Glide.with(this)
            .load(url)
            .placeholder(placeholder)
            .error(R.drawable.egg)
            .into(this)
    }

    private fun FragmentPokemonSpritesBinding.loadState(state: InfoState, retry: (Int) -> Unit){
        val isInitial = state is InfoState.Initial
        val isLoading = state is InfoState.Loading
        val isLoaded = state is InfoState.Loaded
        val isError = state is InfoState.Error

        pokemonDefault.isVisible = isLoaded
        pokemonShiny.isVisible = isLoaded
        initialState.isVisible = isInitial
        progressBar.isVisible = isLoading
        layoutRetry.isVisible = isError

        when(state){
            is InfoState.Initial -> {}
            is InfoState.Loading -> {}
            is InfoState.Loaded -> binding.bind(state.info.sprites)
            is InfoState.Error -> {
                errorMessage.text = state.error.localizedMessage
                btnRetry.setOnClickListener { retry(state.pokemonID) }
            }
        }
    }


    private fun observeData(viewedState: StateFlow<InfoState>, retry: (Int) -> Unit) {
        lifecycleScope.launch {
            viewedState.flowWithLifecycle(lifecycle, Lifecycle.State.STARTED).distinctUntilChanged()
                .collect{ binding.loadState(it, retry) }
        }
    }
}