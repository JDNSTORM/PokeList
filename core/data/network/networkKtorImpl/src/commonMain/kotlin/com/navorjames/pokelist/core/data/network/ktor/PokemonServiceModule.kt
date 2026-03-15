package com.navorjames.pokelist.core.data.network.ktor

import com.navorjames.pokelist.core.data.network.PokemonService
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.dsl.module

val pokemonServiceModule = module {
    single {
        HttpClient(PlatformEngine) {
            install(ContentNegotiation) {
                json(
                    Json {
                        ignoreUnknownKeys = true
                    }
                )
            }
        }
    }
    single<PokemonService> {
        PokemonServiceImpl(get())
    }
}