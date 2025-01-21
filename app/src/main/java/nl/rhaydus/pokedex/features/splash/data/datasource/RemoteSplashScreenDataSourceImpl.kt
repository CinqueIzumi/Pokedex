package nl.rhaydus.pokedex.features.splash.data.datasource

import nl.rhaydus.pokedex.core.domain.model.Pokemon
import nl.rhaydus.pokedex.features.splash.data.mapper.toPokemonList
import nl.rhaydus.pokedex.features.splash.data.network.SplashScreenApiService
import nl.rhaydus.pokedex.features.splash.domain.exception.SplashScreenException
import javax.inject.Inject

class RemoteSplashScreenDataSourceImpl @Inject constructor(
    private val splashScreenApiService: SplashScreenApiService
) : RemoteSplashScreenDataSource {
    override suspend fun initializeAllPokemon(): List<Pokemon> {
        val response = splashScreenApiService.getAllPokemonFromApi()

        if (!response.isSuccessful) {
            throw SplashScreenException.FailedResponse
        }

        return response.body()?.toPokemonList() ?: throw SplashScreenException.EmptyResponseBody
    }
}