package com.navorjames.pokelist.core.data.network.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.navorjames.pokelist.core.data.network.data.serialization.UrlIdSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = Pokemon.TABLE_NAME)
data class Pokemon(
    @PrimaryKey
    @SerialName("url")
    @Serializable(UrlIdSerializer::class)
    val id: Int = 0,
    @SerialName("name")
    val name: String = ""
) {
    companion object {
        const val TABLE_NAME = "Pokemons"
    }
}