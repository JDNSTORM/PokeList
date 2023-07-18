package com.example.pokelist.ui

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.viewpager2.widget.ViewPager2
import com.example.pokelist.adapters.FragmentsAdapter
import com.example.pokelist.adapters.PokemonsAdapter
import com.example.pokelist.adapters.PokemonsLoadStateAdapter
import com.example.pokelist.databinding.ActivityMainBinding
import com.example.pokelist.databinding.SectionPokeListBinding
import com.example.pokelist.ui.fragments.PokemonFragment
import com.example.pokelist.ui.fragments.PokemonSpritesFragment
import com.example.pokelist.viewmodels.MainViewModel
import com.example.pokelist.viewmodels.repositories.poke_api.entities.Pokemon
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel: MainViewModel by viewModels()
        binding.setupUI(
            viewModel.pokeListPagingData,
            viewModel.getInfo
        )
    }

    private fun ActivityMainBinding.setupUI(
        data: Flow<PagingData<Pokemon>>,
        viewPokemon: (Int) -> Unit
    ){
        setupFragments()
        listHolder.setupRecyclerView(data, viewPokemon)
    }

    private fun ActivityMainBinding.setupFragments(){
        val fragmentAdapter = FragmentsAdapter(supportFragmentManager, lifecycle)
        fragmentAdapter.addFragment(
            PokemonFragment(),
            PokemonSpritesFragment()
        )

        pokemonInfoPager.adapter = fragmentAdapter

        TabLayoutMediator(infoTabLayout, pokemonInfoPager){ _, _ -> }.attach()
    }

    private fun SectionPokeListBinding.setupRecyclerView(
        data: Flow<PagingData<Pokemon>>,
        viewPokemon: (Int) -> Unit
    ){
        val pagingAdapter = PokemonsAdapter(viewPokemon)
        pokemonList.adapter = pagingAdapter.withLoadStateFooter(
            PokemonsLoadStateAdapter { pagingAdapter.retry() }
        )

        lifecycleScope.launch {
            data.flowWithLifecycle(lifecycle, Lifecycle.State.RESUMED)
                .collectLatest(pagingAdapter::submitData)
        }

        btnRetry.setOnClickListener { pagingAdapter.retry() }
        lifecycleScope.launch {
            pagingAdapter.loadStateFlow.collectLatest{monitorListState(it)}
        }
    }

    private fun SectionPokeListBinding.monitorListState(state: CombinedLoadStates){
        val isListEmpty = state.source.refresh is LoadState.NotLoading
                && state.append is LoadState.NotLoading
                && pokemonList.adapter?.itemCount == 0
        val isLoading = (state.refresh is LoadState.Loading
                || state.append is LoadState.Loading)
                && pokemonList.adapter?.itemCount == 0
        val isError = state.refresh is LoadState.Error
                && pokemonList.adapter?.itemCount == 0
        listEmpty.isVisible = isListEmpty && !isError && !isLoading
        pokemonList.isVisible = !isListEmpty && !isLoading && !isError
        progressBar.isVisible = isLoading
        layoutRetry.isVisible = isError

        val errorState = state.refresh as? LoadState.Error
//            ?: state.source.prepend as? LoadState.Error
//            ?: state.append as? LoadState.Error
//            ?: state.prepend as? LoadState.Error
        errorState?.let {
            errorMessage.text = it.error.localizedMessage
        }
    }
}