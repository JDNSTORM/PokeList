package com.navorjames.pokelist.core.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import com.navorjames.pokelist.core.data.network.data.PokemonInfo

@Dao
interface PokemonInfoDAO {
    @Insert(onConflict = REPLACE)
    suspend fun insert(info: PokemonInfo)

    @Query("SELECT * FROM `${PokemonInfoDatabase.POKEMON_INFO_TABLE}` WHERE id=:id")
    suspend fun getByID(id: Int): PokemonInfo?

    @Delete
    suspend fun delete(info: PokemonInfo)

    @Query("DELETE FROM `${PokemonInfoDatabase.POKEMON_INFO_TABLE}`")
    suspend fun cleanTable()
}