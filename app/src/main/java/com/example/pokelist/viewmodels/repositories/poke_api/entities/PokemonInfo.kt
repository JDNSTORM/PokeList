package com.example.pokelist.viewmodels.repositories.poke_api.entities


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.pokelist.viewmodels.repositories.poke_rooms.PokemonInfoDatabase.Companion.POKEMON_INFO_TABLE
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = POKEMON_INFO_TABLE)
data class PokemonInfo(
    @SerializedName("abilities")
    @SerialName("abilities")
    val abilities: List<Ability> = listOf(),
    @SerializedName("height")
    @SerialName("height")
    val height: Int = 0,
    @PrimaryKey(false)
    @SerializedName("id")
    @SerialName("id")
    val id: Int = 0,
    @SerializedName("moves")
    @SerialName("moves")
    val moves: List<Moves> = listOf(),
    @SerializedName("name")
    @SerialName("name")
    val name: String = "",
    @SerializedName("order")
    @SerialName("order")
    val order: Int = 0,
    @SerializedName("sprites")
    @SerialName("sprites")
    val sprites: Sprites = Sprites(),
)