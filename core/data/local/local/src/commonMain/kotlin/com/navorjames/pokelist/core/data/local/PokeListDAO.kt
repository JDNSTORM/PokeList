package com.navorjames.pokelist.core.data.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import com.navorjames.pokelist.core.data.network.data.Pokemon

@Dao
interface PokeListDAO {
    @Insert(onConflict = REPLACE)
    suspend fun insert(vararg pokemons: Pokemon)

    @Insert(onConflict = REPLACE)
    suspend fun insertAll(pokeList: List<Pokemon>)

    @Query("SELECT * FROM Pokemons ORDER BY id ASC")
    fun getPokeList(): PagingSource<Int, Pokemon>

    @Query("SELECT * FROM Pokemons ORDER BY id ASC LIMIT :limit OFFSET :offset")
    suspend fun getPokeList(offset: Int, limit: Int): List<Pokemon>

    @Query("DELETE FROM Pokemons")
    suspend fun clearTable()
}