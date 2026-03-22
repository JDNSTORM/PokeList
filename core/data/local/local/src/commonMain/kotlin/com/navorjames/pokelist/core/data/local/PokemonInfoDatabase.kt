package com.navorjames.pokelist.core.data.local

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.navorjames.pokelist.core.data.local.data.PokemonPagingRemoteKeys
import com.navorjames.pokelist.core.data.network.data.Pokemon
import com.navorjames.pokelist.core.data.network.data.PokemonInfo

@Database(
    entities = [
        PokemonInfo::class,
        Pokemon::class,
        PokemonPagingRemoteKeys::class
    ],
    version = 2,
    exportSchema = false
)
@TypeConverters(PokemonInfoSerializationConverter::class)
@ConstructedBy(PokemonInfoDatabaseConstructor::class)
abstract class PokemonInfoDatabase(): RoomDatabase() {
    abstract fun pokemonInfoDAO(): PokemonInfoDAO
    abstract fun pokemonDao(): PokemonDao

    companion object {
        const val DB_NAME = "pokemon-database"
    }
}