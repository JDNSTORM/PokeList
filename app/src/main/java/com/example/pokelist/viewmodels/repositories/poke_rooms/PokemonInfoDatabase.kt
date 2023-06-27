package com.example.pokelist.viewmodels.repositories.poke_rooms

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.pokelist.viewmodels.repositories.poke_api.entities.Pokemon
import com.example.pokelist.viewmodels.repositories.poke_api.entities.PokemonInfo

@Database(entities = [PokemonInfo::class, Pokemon::class], version = 2, exportSchema = false)
@TypeConverters(PokemonInfoTypeConverter::class)
abstract class PokemonInfoDatabase(): RoomDatabase() {
    abstract fun pokemonInfoDAO(): PokemonInfoDAO
    abstract fun pokeListDAO(): PokeListDAO

    companion object{
        const val DB_NAME = "pokemon-database"
        const val POKEMON_INFO_TABLE = "pokemon-info"
        const val POKELIST_TABLE = "pokemon-list"
    }
}