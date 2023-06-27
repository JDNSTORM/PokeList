package com.example.pokelist.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.pokelist.R
import com.example.pokelist.adapters.FragmentsAdapter
import com.example.pokelist.databinding.ActivityMainBinding
import com.example.pokelist.ui.fragments.PokeListFragment
import com.example.pokelist.ui.fragments.PokemonFragment
import com.example.pokelist.ui.fragments.PokemonSpritesFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.setupFragments()
    }

    private fun ActivityMainBinding.setupFragments(){
        supportFragmentManager.beginTransaction().add(listHolder.id, PokeListFragment()).commit()
        val fragmentAdapter = FragmentsAdapter(supportFragmentManager, lifecycle)
        fragmentAdapter.addFragment(
            PokemonFragment(),
            PokemonSpritesFragment()
        )

        pokemonInfoPager.adapter = fragmentAdapter
    }
}