package com.example.pokelist.viewmodels.repositories.poke_api.retrofit

import com.navorjames.pokelist.core.data.network.PokemonService
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory

//@Module
//@InstallIn(SingletonComponent::class)
object PokeAPIModule {
    private const val BASE_URL = "https://pokeapi.co/api/v2/"
    const val QUERY_OFFSET = "offset"
    const val DEFAULT_OFFSET = PokemonService.DEFAULT_OFFSET
    const val QUERY_LIMIT = "limit"
    const val DEFAULT_LIMIT = 20
    const val PATH_POKEMON_ID = "pokemonId"

//    @Singleton
//    @Provides
    fun providePokemonService(): PokemonService {
        val json = Json {
            ignoreUnknownKeys = true // API often has more fields than your data class
            isLenient = true
            coerceInputValues = true
        }

        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        val client = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()


        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(
                json.asConverterFactory(
                    "application/json".toMediaType()
                )
            )
            .build()

        return retrofit.create(PokemonServiceImpl::class.java)
    }
}