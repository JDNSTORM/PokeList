package com.example.pokelist.viewmodels.repositories.poke_api

import com.navorjames.pokelist.core.data.network.ktor.pokemonServiceModule
import org.koin.dsl.module

val remoteDataSourceModule = module {
    includes(pokemonServiceModule)
    single {
        RemoteDataSource(
            service = get()
        )
    }
}