package com.navorjames.pokelist.core.data.local

import androidx.paging.PagingSource
import com.navorjames.pokelist.core.data.network.data.Pokemon
import com.navorjames.pokelist.core.data.network.data.PokemonInfo

class LocalDataSource(
    private val infoDAO: PokemonInfoDAO,
    private val listDAO: PokeListDAO
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

    fun getPokeListPagingSource(): PagingSource<Int, Pokemon> = listDAO.getPokeList()

    suspend fun getList(offset: Int, limit: Int) = listDAO.getPokeList(offset, limit)

    suspend fun insertList(list: List<Pokemon>) = listDAO.insertAll(list)

    suspend fun clearPokeList() = listDAO.clearTable()
}