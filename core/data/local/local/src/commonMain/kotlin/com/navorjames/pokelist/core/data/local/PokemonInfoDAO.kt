package com.navorjames.pokelist.core.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import com.navorjames.pokelist.core.data.network.data.PokemonInfo
import kotlinx.coroutines.flow.Flow

@Dao
interface PokemonInfoDAO {
    @Insert(onConflict = REPLACE)
    suspend fun insert(info: PokemonInfo)

    @Query("SELECT * FROM PokemonInfos WHERE id=:id")
    suspend fun getByID(id: Int): PokemonInfo?

    @Query("SELECT * FROM PokemonInfos WHERE id=:id")
    fun read(id: Int): Flow<PokemonInfo?>

    @Delete
    suspend fun delete(info: PokemonInfo)

    @Query("DELETE FROM PokemonInfos")
    suspend fun cleanTable()
}