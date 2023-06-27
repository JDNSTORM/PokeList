package com.example.pokelist.viewmodels.repositories.poke_rooms

import androidx.paging.PagingSource
import com.example.pokelist.viewmodels.repositories.poke_api.entities.Pokemon
import com.example.pokelist.viewmodels.repositories.poke_api.entities.PokemonInfo
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val infoDAO: PokemonInfoDAO,
    private val listDAO: PokeListDAO
) {
    suspend fun insertPokemonInfo(info: PokemonInfo){
        infoDAO.insert(info)
    }

    suspend fun getPokemonInfoByID(id: Int): PokemonInfo?{
        return infoDAO.getByID(id)
    }

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