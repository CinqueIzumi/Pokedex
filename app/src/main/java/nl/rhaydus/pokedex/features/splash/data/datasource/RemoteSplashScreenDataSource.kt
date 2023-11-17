package nl.rhaydus.pokedex.features.splash.data.datasource

import nl.rhaydus.pokedex.core.domain.model.Pokemon
import nl.rhaydus.pokedex.features.splash.domain.exception.SplashScreenException

interface RemoteSplashScreenDataSource {
    /**
     * @exception [SplashScreenException.EmptyResponseBody] Response was successful with empty body
     * @exception [SplashScreenException.FailedResponse] Response was unsuccessful
     */
    suspend fun initializeAllPokemon(): List<Pokemon>
}