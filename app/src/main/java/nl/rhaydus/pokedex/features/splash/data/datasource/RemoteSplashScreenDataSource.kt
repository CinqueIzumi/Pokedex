package nl.rhaydus.pokedex.features.splash.data.datasource

import nl.rhaydus.pokedex.features.pokemon_display.domain.model.Pokemon

interface RemoteSplashScreenDataSource {
    /**
     * @throws [SplashScreenException.EmptyResponseBody] Response was successful with empty body
     * @throws [SplashScreenException.FailedResponse] Response was unsuccessful
     */
    suspend fun initializeAllPokemon(): List<Pokemon>
}