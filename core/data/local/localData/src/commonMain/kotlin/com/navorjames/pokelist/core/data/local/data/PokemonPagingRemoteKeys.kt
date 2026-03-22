package com.navorjames.pokelist.core.data.local.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.navorjames.pokelist.core.data.network.data.Pokemon
import com.navorjames.pokelist.core.data.network.data.paging.PagedItems
import com.navorjames.pokelist.core.data.network.data.paging.PagingRemoteKeys

typealias PagedPokemons = PagedItems<Int, Pokemon>

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = Pokemon::class,
            parentColumns = [Pokemon.COLUMN_ID],
            childColumns = [PokemonPagingRemoteKeys.COLUMN_POKEMON_ID],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class PokemonPagingRemoteKeys(
    @PrimaryKey
    @ColumnInfo(name = COLUMN_POKEMON_ID)
    val pokemonId: Int,
    override val currentKey: Int?,
    override val prevKey: Int?,
    override val nextKey: Int?,
    override val position: Int
): PagingRemoteKeys<Int> {
    companion object {
        const val COLUMN_POKEMON_ID = "pokemonId"

        fun PagedPokemons.remoteKeys(
            startingPosition: Int
        ): List<PokemonPagingRemoteKeys> = items.mapIndexed { index, pokemon ->
            PokemonPagingRemoteKeys(
                pokemonId = pokemon.id,
                currentKey = key,
                prevKey = prevKey,
                nextKey = nextKey,
                position = startingPosition + index
            )
        }

    }
}
