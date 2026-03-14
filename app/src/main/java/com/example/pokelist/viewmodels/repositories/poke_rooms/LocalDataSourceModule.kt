package com.example.pokelist.viewmodels.repositories.poke_rooms

import org.koin.dsl.module

val localDataSourceModule = module {
    includes(databaseModule)
    single {
        LocalDataSource(
            infoDAO = get(),
            listDAO = get()
        )
    }
}