package com.example.pokelist.viewmodels.repositories.poke_rooms

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import com.example.pokelist.viewmodels.repositories.poke_api.entities.Pokemon
import com.example.pokelist.viewmodels.repositories.poke_rooms.PokemonInfoDatabase.Companion.POKELIST_TABLE

@Dao
interface PokeListDAO {
    @Insert(onConflict = REPLACE)
    suspend fun insert(vararg pokemons: Pokemon)

    @Insert(onConflict = REPLACE)
    suspend fun insertAll(pokeList: List<Pokemon>)

    @Query("SELECT * FROM `$POKELIST_TABLE` ORDER BY dbIndex ASC")
    fun getPokeList(): PagingSource<Int, Pokemon>

    @Query("SELECT * FROM `$POKELIST_TABLE` ORDER BY dbIndex ASC LIMIT :limit OFFSET :offset")
    suspend fun getPokeList(offset: Int, limit: Int): List<Pokemon>

    @Query("DELETE FROM `$POKELIST_TABLE`")
    suspend fun clearTable()
}