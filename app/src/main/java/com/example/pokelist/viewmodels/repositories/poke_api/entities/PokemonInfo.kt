package com.example.pokelist.viewmodels.repositories.poke_api.entities


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.pokelist.viewmodels.repositories.poke_rooms.PokemonInfoDatabase.Companion.POKEMON_INFO_TABLE
import com.google.gson.annotations.SerializedName
import ph.projects.navor_jamesdave.apis.versions.with_gson.pokeapi_entities.Sprites

@Entity(tableName = POKEMON_INFO_TABLE)
data class PokemonInfo(
    @SerializedName("abilities")
    val abilities: List<Ability> = listOf(),
    @SerializedName("height")
    val height: Int = 0,
    @PrimaryKey(false)
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("moves")
    val moves: List<Moves> = listOf(),
    @SerializedName("name")
    val name: String = "",
    @SerializedName("order")
    val order: Int = 0,
    @SerializedName("sprites")
    val sprites: Sprites = Sprites(),
)