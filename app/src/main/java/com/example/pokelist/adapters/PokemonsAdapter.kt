package com.example.pokelist.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.example.pokelist.databinding.ItemPokemonNameBinding
import com.example.pokelist.viewmodels.repositories.poke_api.entities.Pokemon

class PokemonsAdapter(
    private val onView: (Int) -> Unit
): PagingDataAdapter<Pokemon, PokemonsAdapter.ViewHolder>(DiffCallBack) {

    class ViewHolder(val binding: ViewBinding): RecyclerView.ViewHolder(binding.root)

    companion object{
        private val DiffCallBack = object: DiffUtil.ItemCallback<Pokemon>(){
            override fun areItemsTheSame(oldItem: Pokemon, newItem: Pokemon): Boolean {
                return oldItem.name == newItem.name
            }

            override fun areContentsTheSame(oldItem: Pokemon, newItem: Pokemon): Boolean {
                return oldItem == newItem
            }

        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val binding = holder.binding as ItemPokemonNameBinding
        val pokemon = getItem(position) ?: return
        binding.bind(pokemon)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemPokemonNameBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    private fun ItemPokemonNameBinding.bind(pokemon: Pokemon){
        pokemonId.text = pokemon.id()
        name.text = pokemon.name.uppercase()
        btnView.setOnClickListener { onView(pokemon.id().toInt()) }
    }
}