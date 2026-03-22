package com.navorjames.pokelist.core.data.network.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.navorjames.pokelist.core.data.network.data.serialization.UrlIdSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = Pokemon.TABLE_NAME)
data class Pokemon(
    @PrimaryKey
    @ColumnInfo(name = COLUMN_ID)
    @SerialName("url")
    @Serializable(UrlIdSerializer::class)
    val id: Int = 0,
    @SerialName("name")
    val name: String = ""
) {
    companion object {
        const val TABLE_NAME = "Pokemons"
        const val COLUMN_ID = "id"
    }
}