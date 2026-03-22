package com.navorjames.pokelist.core.data.local

import androidx.paging.PagingSource
import com.navorjames.pokelist.core.data.local.data.PagedPokemons
import com.navorjames.pokelist.core.data.network.data.Pokemon
import com.navorjames.pokelist.core.data.network.data.PokemonInfo

class LocalDataSource(
    private val infoDAO: PokemonInfoDAO,
    private val pokemonDao: PokemonDao
) {
    suspend fun insertPokemonInfo(info: PokemonInfo){
        infoDAO.insert(info)
    }

    suspend fun getPokemonInfoByID(id: Int): PokemonInfo?{
        return infoDAO.getByID(id)
    }

    fun readPokemonInfoByID(id: Int) = infoDAO.read(id)

    suspend fun deletePokemonInfo(info: PokemonInfo){
        infoDAO.delete(info)
    }

    suspend fun deleteAllPokemonInfo(){
        infoDAO.cleanTable()
    }

    fun getPokemonPagingSource(): PagingSource<Int, Pokemon> = pokemonDao.getPagingSource()

    suspend fun hasCachedPokemons() = pokemonDao.hasPokemons()

    suspend fun getRemoteKeys(pokemonId: Int) = pokemonDao.getRemoteKeys(pokemonId)

    suspend fun insertPagedPokemons(
        pagedPokemons: PagedPokemons,
        clearData: Boolean
    ) = pokemonDao.insertPagedPokemons(pagedPokemons, clearData)

    suspend fun clearPokemons() = pokemonDao.clearPokemons()
}