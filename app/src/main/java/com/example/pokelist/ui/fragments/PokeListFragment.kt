package com.example.pokelist.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.PagingData
import com.example.pokelist.R
import com.example.pokelist.adapters.PokemonsAdapter
import com.example.pokelist.adapters.PokemonsLoadStateAdapter
import com.example.pokelist.databinding.FragmentPokeListBinding
import com.example.pokelist.viewmodels.MainViewModel
import com.example.pokelist.viewmodels.repositories.poke_api.entities.Pokemon
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PokeListFragment : BaseFragment<FragmentPokeListBinding>() {
    override fun inflateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentPokeListBinding = FragmentPokeListBinding.inflate(
        inflater, container, false
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
        binding.setupRecyclerView(viewModel.pokeListPagingData, viewModel.getInfo)
    }

    private fun FragmentPokeListBinding.setupRecyclerView(
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

    private fun FragmentPokeListBinding.monitorListState(state: CombinedLoadStates){
        state.track()
        val isListEmpty = state.source.refresh is LoadState.NotLoading
                && state.append is LoadState.NotLoading
                && pokemonList.adapter?.itemCount == 0
        val isLoading = (state.source.refresh is LoadState.Loading
                || state.append is LoadState.Loading)
                && pokemonList.adapter?.itemCount == 0
        val isError = state.refresh is LoadState.Error
        listEmpty.isVisible = isListEmpty
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

    private fun CombinedLoadStates.track(){
        Log.d("Prepend", prepend.toString())
        Log.d("Refresh", refresh.toString())
        Log.d("Append", append.toString())
        Log.d("SourcePrepend", source.prepend.toString())
        Log.d("SourceRefresh", source.refresh.toString())
        Log.d("SourceAppend", source.append.toString())
        Log.d("MediatorPrepend", mediator?.prepend.toString())
        Log.d("MediatorRefresh", mediator?.refresh.toString())
        Log.d("MediatorAppend", mediator?.append.toString())
    }
}