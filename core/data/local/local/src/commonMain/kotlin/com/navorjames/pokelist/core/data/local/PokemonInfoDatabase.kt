package com.navorjames.pokelist.core.data.local

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.navorjames.pokelist.core.data.network.data.Pokemon
import com.navorjames.pokelist.core.data.network.data.PokemonInfo

@Database(
    entities = [PokemonInfo::class, Pokemon::class],
    version = 2,
    exportSchema = false
)
@TypeConverters(PokemonInfoSerializationConverter::class)
@ConstructedBy(PokemonInfoDatabaseConstructor::class)
abstract class PokemonInfoDatabase(): RoomDatabase() {
    abstract fun pokemonInfoDAO(): PokemonInfoDAO
    abstract fun pokeListDAO(): PokeListDAO

    companion object{
        const val DB_NAME = "pokemon-database"
        const val POKEMON_INFO_TABLE = PokemonInfo.TABLE_NAME
        const val POKELIST_TABLE = Pokemon.TABLE_NAME
    }
}