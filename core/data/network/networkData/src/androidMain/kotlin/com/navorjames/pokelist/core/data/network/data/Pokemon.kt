package com.navorjames.pokelist.core.data.network.data


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = Pokemon.TABLE_NAME)
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

    companion object {
        const val TABLE_NAME = "pokemon-list"
    }
}