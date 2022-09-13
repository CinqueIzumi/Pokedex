package nl.rhaydus.pokedex.features.user_generator.data.network

import nl.rhaydus.pokedex.features.core.BASE_URL
import nl.rhaydus.pokedex.features.user_generator.data.model.PokemonApiModel
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface PokemonApiService {
    @GET("pokemon/{id}")
    suspend fun getRandomPokemon(@Path("id") id: Int): Response<PokemonApiModel>

    companion object {
        fun getPokemonApiService(): PokemonApiService {
            val retrofit = Retrofit
                .Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            return retrofit.create(PokemonApiService::class.java)
        }
    }
}