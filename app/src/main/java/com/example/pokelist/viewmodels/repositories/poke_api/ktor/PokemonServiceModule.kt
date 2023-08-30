package com.example.pokelist.viewmodels.repositories.poke_api.ktor

import com.example.pokelist.viewmodels.repositories.poke_api.PokemonService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PokemonServiceModule {
    @Singleton
    @Provides
    fun provideHttpClient(): HttpClient = HttpClient(Android) {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
            })
        }
    }

    @Singleton
    @Provides
    fun providePokemonService(client: HttpClient): PokemonService = PokemonServiceImpl(client)
}