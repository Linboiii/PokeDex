package com.example.pokedex.service

import com.example.pokedex.model.PokemonSearchResponse
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface PokemonPriceApi {
    @GET("cards")
    suspend fun searchCards(
        @Query("search") query: String,
        @Query("limit") limit: Int = 20
    ): PokemonSearchResponse
}

object PokemonService {
    private const val BASE_URL = "https://www.pokemonpricetracker.com/api/v2/"
    private const val API_KEY = "pokeprice_free_64e85db86a0c560e8d1cddac0d86f4eef317c684f093799f"

    private val authInterceptor = Interceptor { chain ->
        val request = chain.request().newBuilder()
            .addHeader("Authorization", "Bearer $API_KEY")
            .addHeader("x-api-key", API_KEY)
            .build()
        chain.proceed(request)
    }

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(authInterceptor)
        .build()

    val api: PokemonPriceApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PokemonPriceApi::class.java)
    }
}