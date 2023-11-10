package nl.rhaydus.pokedex.features.splash.data.datasource

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import nl.rhaydus.pokedex.features.pokemon_display.domain.model.Pokemon
import nl.rhaydus.pokedex.features.splash.data.mapper.toPokemonList
import nl.rhaydus.pokedex.features.splash.data.network.SplashScreenApiService
import nl.rhaydus.pokedex.features.splash.domain.exception.SplashScreenException
import javax.inject.Inject

class RemoteSplashScreenDataSourceImpl @Inject constructor(
    private val splashScreenApiService: SplashScreenApiService
) : RemoteSplashScreenDataSource {
    override suspend fun initializeAllPokemon(): List<Pokemon> {
        val response = withContext(Dispatchers.IO) {
            splashScreenApiService.getAllPokemonFromApi()
        }

        if (!response.isSuccessful) {
            throw SplashScreenException.FailedResponse
        }

        return response.body()?.toPokemonList() ?: throw SplashScreenException.EmptyResponseBody
    }
}