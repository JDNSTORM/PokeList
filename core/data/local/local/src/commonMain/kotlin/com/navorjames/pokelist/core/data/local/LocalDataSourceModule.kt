package com.navorjames.pokelist.core.data.local

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