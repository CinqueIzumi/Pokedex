package nl.rhaydus.pokedex.features.pokemon_display.data.network

import nl.rhaydus.pokedex.features.pokemon_display.data.network.response.IndividualPokemonResponse
import nl.rhaydus.pokedex.features.pokemon_display.data.network.response.IndividualPokemonSpeciesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface PokemonApiService {
    @GET("pokemon/{id}")
    suspend fun getSpecificPokemon(@Path("id") id: Int): Response<IndividualPokemonResponse>

    @GET("pokemon-species/{id}")
    suspend fun getSpecificPokemonSpecies(@Path("id") id: Int): Response<IndividualPokemonSpeciesResponse>
}