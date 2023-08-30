package com.example.pokelist.viewmodels.repositories.poke_api.entities


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.pokelist.viewmodels.repositories.poke_rooms.PokemonInfoDatabase.Companion.POKELIST_TABLE
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = POKELIST_TABLE)
data class Pokemon(
    @PrimaryKey(true)
    val dbIndex: Long = 0,
    @SerializedName("name")
    @SerialName("name")
    val name: String = "",
    @SerializedName("url")
    @SerialName("url")
    val url: String = ""
){
    fun id(): String{
        val link = "https://pokeapi.co/api/v2/pokemon/"
        return url.substring(link.length until url.lastIndex)
    }

    constructor(name: String, url: String): this(0, name, url)
}