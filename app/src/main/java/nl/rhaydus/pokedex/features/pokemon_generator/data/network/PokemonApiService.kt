package nl.rhaydus.pokedex.features.pokemon_generator.data.network

import nl.rhaydus.pokedex.features.pokemon_generator.data.model.PokemonApiModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface PokemonApiService {
    @GET("pokemon/{id}")
    suspend fun getSpecificPokemon(@Path("id") id: Int): Response<PokemonApiModel>
}