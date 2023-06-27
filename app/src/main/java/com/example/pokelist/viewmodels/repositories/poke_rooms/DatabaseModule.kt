package com.example.pokelist.viewmodels.repositories.poke_rooms

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule{
    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext context: Context
    ): PokemonInfoDatabase {
        return Room.databaseBuilder(
            context,
            PokemonInfoDatabase::class.java,
            PokemonInfoDatabase.DB_NAME
        ).fallbackToDestructiveMigration().build()
    }

    @Singleton
    @Provides
    fun provideDAO(db: PokemonInfoDatabase): PokemonInfoDAO = db.pokemonInfoDAO()

    @Singleton
    @Provides
    fun providePokeListDAO(db: PokemonInfoDatabase): PokeListDAO = db.pokeListDAO()
}