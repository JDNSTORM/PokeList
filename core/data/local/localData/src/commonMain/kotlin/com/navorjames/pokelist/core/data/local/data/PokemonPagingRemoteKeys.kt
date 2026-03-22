package com.navorjames.pokelist.core.data.local.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.navorjames.pokelist.core.data.network.data.Pokemon
import com.navorjames.pokelist.core.data.network.data.paging.PagedItems
import com.navorjames.pokelist.core.data.network.data.paging.PagedItems.Companion.offsetBasedRemoteKeys
import com.navorjames.pokelist.core.data.network.data.paging.PagingRemoteKeys

typealias PagedPokemons = PagedItems<Int, Pokemon>

/**
 * Entity class representing the remote paging keys for the [Pokemon] data.
 *
 * This class is used by the Paging library's `RemoteMediator` to store pagination metadata
 * (such as previous and next page keys) in the local database. It facilitates consistent
 * pagination across app restarts and network refreshes by maintaining the state of
 * loaded pages for each individual Pokemon entry.
 *
 * @property pokemonId The unique identifier of the Pokemon, acting as the primary key.
 * @property currentKey The offset used to fetch the current page.
 * @property prevKey The offset used to fetch the previous page, or null if there is no previous page.
 * @property nextKey The offset used to fetch the next page, or null if there is no next page.
 * @property position The absolute position of the item within the entire dataset.
 */
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

        fun PagedPokemons.remoteKeys(): List<PokemonPagingRemoteKeys> = offsetBasedRemoteKeys { pokemon, position ->
            PokemonPagingRemoteKeys(
                pokemonId = pokemon.id,
                currentKey = key,
                prevKey = prevKey,
                nextKey = nextKey,
                position = position
            )
        }

    }
}
