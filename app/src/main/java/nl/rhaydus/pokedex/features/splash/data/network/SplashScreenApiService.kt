package nl.rhaydus.pokedex.features.splash.data.network

import nl.rhaydus.pokedex.core.data.constant.HIGHEST_POKEMON_ID
import nl.rhaydus.pokedex.features.splash.data.network.response.GlobalPokemonResponse
import retrofit2.Response
import retrofit2.http.GET

interface SplashScreenApiService {
    @GET("pokemon?limit=$HIGHEST_POKEMON_ID&offset=0")
    suspend fun getAllPokemonFromApi(): Response<GlobalPokemonResponse>
}