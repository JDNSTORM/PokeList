package com.navorjames.pokelist.core.data.network

import org.koin.dsl.module

val remoteDataSourceModule = module {
    single {
        RemoteDataSource(
            service = get()
        )
    }
}