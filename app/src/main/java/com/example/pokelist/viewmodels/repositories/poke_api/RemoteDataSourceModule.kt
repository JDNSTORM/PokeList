package com.example.pokelist.viewmodels.repositories.poke_api

import com.example.pokelist.viewmodels.repositories.poke_api.ktor.pokemonServiceModule
import org.koin.dsl.module

val remoteDataSourceModule = module {
    includes(pokemonServiceModule)
    single {
        RemoteDataSource(
            service = get()
        )
    }
}