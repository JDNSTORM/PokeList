package com.navorjames.pokelist.core.data.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.navorjames.pokelist.core.data.local.data.PagedPokemons
import com.navorjames.pokelist.core.data.local.data.PokemonPagingRemoteKeys
import com.navorjames.pokelist.core.data.local.data.PokemonPagingRemoteKeys.Companion.remoteKeys
import com.navorjames.pokelist.core.data.network.data.Pokemon

@Dao
abstract class PokemonDao {

    @Transaction
    open suspend fun insertPagedPokemons(
        pagedPokemons: PagedPokemons,
        clearData: Boolean
    ) {
        if (clearData) clearPokemons()
        val remoteKeys = pagedPokemons.remoteKeys()

        upsertPokemons(pagedPokemons.items)
        upsertRemoteKeys(remoteKeys)
    }

    @Upsert
    protected abstract suspend fun upsertPokemons(pokemons: List<Pokemon>)

    @Transaction
    @Query("SELECT Pokemons.* FROM Pokemons " +
            "INNER JOIN PokemonPagingRemoteKeys RK ON RK.pokemonId = Pokemons.id " +
            "ORDER BY RK.position ASC")
    abstract fun getPagingSource(): PagingSource<Int, Pokemon>

    @Query("SELECT EXISTS(SELECT 1 FROM Pokemons LIMIT 1)")
    abstract suspend fun hasPokemons(): Boolean

    @Query("DELETE FROM Pokemons")
    abstract suspend fun clearPokemons()

    @Upsert
    protected abstract suspend fun upsertRemoteKeys(keys: List<PokemonPagingRemoteKeys>)

    @Query("SELECT * FROM PokemonPagingRemoteKeys " +
            "WHERE pokemonId = :pokemonId")
    abstract suspend fun getRemoteKeys(pokemonId: Int): PokemonPagingRemoteKeys?

    @Query("SELECT MAX(position) FROM PokemonPagingRemoteKeys " +
            "WHERE currentKey = :key")
    protected abstract suspend fun getMaxPositionFromKey(key: Int?): Int?

    @Query("SELECT MIN(position) FROM PokemonPagingRemoteKeys " +
            "WHERE currentKey = :key")
    protected abstract suspend fun getMinPositionFromKey(key: Int?): Int?

    @Query("DELETE FROM PokemonPagingRemoteKeys")
    protected abstract suspend fun clearRemoteKeys()
}