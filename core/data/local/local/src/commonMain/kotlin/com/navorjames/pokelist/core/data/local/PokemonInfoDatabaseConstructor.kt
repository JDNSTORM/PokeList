package com.navorjames.pokelist.core.data.local

import androidx.room.RoomDatabaseConstructor

@Suppress("KotlinNoActualForExpect")
internal expect object PokemonInfoDatabaseConstructor : RoomDatabaseConstructor<PokemonInfoDatabase> {
    override fun initialize(): PokemonInfoDatabase
}
