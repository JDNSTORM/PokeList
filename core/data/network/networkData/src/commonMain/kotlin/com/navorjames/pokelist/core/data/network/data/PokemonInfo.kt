package com.navorjames.pokelist.core.data.network.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = PokemonInfo.TABLE_NAME)
data class PokemonInfo(
    @SerialName("abilities")
    val abilities: List<Ability> = emptyList(),
    @SerialName("height")
    val height: Int = 0,
    @PrimaryKey(false)
    @SerialName("id")
    val id: Int = 0,
    @SerialName("moves")
    val moves: List<Moves> = emptyList(),
    @SerialName("name")
    val name: String = "",
    @SerialName("order")
    val order: Int = 0,
    @SerialName("sprites")
    val sprites: Sprites = Sprites(),
) {
    companion object {
        const val TABLE_NAME = "PokemonInfos"
    }
}