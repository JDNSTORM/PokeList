package com.navorjames.pokelist.core.data.network.ktor

import com.navorjames.pokelist.core.data.network.PokemonService
import com.navorjames.pokelist.core.data.network.remoteDataSourceModule
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.dsl.module

val pokemonServiceModule = module {
    includes(remoteDataSourceModule)
    single {
        HttpClient(PlatformEngine) {
            install(ContentNegotiation) {
                json(
                    Json {
                        ignoreUnknownKeys = true
                    }
                )
            }
            defaultRequest {
                url(PokemonService.BASE_URL)
            }
        }
    }
    single<PokemonService> {
        PokemonServiceImpl(get())
    }
}