package com.navorjames.pokelist.core.data.local

import androidx.room.Room
import androidx.room.RoomDatabase
import org.koin.core.scope.Scope

internal actual fun Scope.platformDatabaseBuilder(): RoomDatabase.Builder<PokemonInfoDatabase> {
    return Room.databaseBuilder(
        context = get(),
        name = PokemonInfoDatabase.DB_NAME
    )
}