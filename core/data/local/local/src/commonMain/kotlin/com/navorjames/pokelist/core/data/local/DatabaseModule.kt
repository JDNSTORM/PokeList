package com.navorjames.pokelist.core.data.local

import androidx.room.RoomDatabase
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import org.koin.core.scope.Scope
import org.koin.dsl.module

val databaseModule = module {
    single {
        platformDatabaseBuilder()
            .setDriver(BundledSQLiteDriver())
            .fallbackToDestructiveMigration(false)
            .build()
    }
    single {
        get<PokemonInfoDatabase>().pokemonInfoDAO()
    }
    single {
        get<PokemonInfoDatabase>().pokeListDAO()
    }
}

internal expect fun Scope.platformDatabaseBuilder(): RoomDatabase.Builder<PokemonInfoDatabase>