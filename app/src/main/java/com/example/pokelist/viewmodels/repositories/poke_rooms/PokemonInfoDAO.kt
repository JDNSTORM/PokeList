package com.example.pokelist.viewmodels.repositories.poke_rooms

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import com.example.pokelist.viewmodels.repositories.poke_api.entities.PokemonInfo
import com.example.pokelist.viewmodels.repositories.poke_rooms.PokemonInfoDatabase.Companion.POKEMON_INFO_TABLE

@Dao
interface PokemonInfoDAO {
    @Insert(onConflict = REPLACE)
    suspend fun insert(info: PokemonInfo)

    @Query("SELECT * FROM `$POKEMON_INFO_TABLE` WHERE id=:id")
    suspend fun getByID(id: Int): PokemonInfo?

    @Delete
    suspend fun delete(info: PokemonInfo)

    @Query("DELETE FROM `$POKEMON_INFO_TABLE`")
    suspend fun cleanTable()
}