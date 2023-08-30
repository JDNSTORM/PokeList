package com.example.pokelist.viewmodels.repositories.poke_api.retrofit

import com.example.pokelist.viewmodels.repositories.poke_api.PokemonService
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PokeAPIModule {
    private const val BASE_URL = "https://pokeapi.co/api/v2/"
    const val QUERY_OFFSET = "offset"
    const val DEFAULT_OFFSET = 0
    const val QUERY_LIMIT = "limit"
    const val DEFAULT_LIMIT = 20
    const val PATH_POKEMON_ID = "pokemonId"

    @Singleton
    @Provides
    fun providePokemonService(): PokemonService {
        val gson = GsonBuilder().setLenient().create()
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        val client = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

        return retrofit.create(PokemonService::class.java)
    }
}