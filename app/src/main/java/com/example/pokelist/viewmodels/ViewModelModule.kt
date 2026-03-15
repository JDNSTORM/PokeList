package com.example.pokelist.viewmodels

import com.example.pokelist.viewmodels.repositories.PokeAPIRepository
import com.example.pokelist.viewmodels.repositories.poke_rooms.localDataSourceModule
import com.navorjames.pokelist.core.data.network.ktor.pokemonServiceModule
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    includes(
        pokemonServiceModule,
        localDataSourceModule
    )
    single {
        PokeAPIRepository(
            remote = get(),
            local = get()
        )
    }
    viewModel {
        MainViewModel(
            repository = get()
        )
    }
}