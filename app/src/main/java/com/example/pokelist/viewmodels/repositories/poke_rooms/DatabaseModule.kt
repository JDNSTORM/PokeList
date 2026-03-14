package com.example.pokelist.viewmodels.repositories.poke_rooms

import androidx.room.Room
import org.koin.dsl.module

val databaseModule = module {
    single {
        Room.databaseBuilder(
            context = get(),
            klass = PokemonInfoDatabase::class.java,
            name = PokemonInfoDatabase.DB_NAME
        ).fallbackToDestructiveMigration(false).build()
    }
    single {
        get<PokemonInfoDatabase>().pokemonInfoDAO()
    }
    single {
        get<PokemonInfoDatabase>().pokeListDAO()
    }
}