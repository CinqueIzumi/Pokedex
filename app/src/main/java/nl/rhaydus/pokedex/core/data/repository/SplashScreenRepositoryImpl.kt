package nl.rhaydus.pokedex.core.data.repository

import nl.rhaydus.pokedex.core.data.datasource.LocalSplashScreenDataSource
import nl.rhaydus.pokedex.core.data.datasource.RemoteSplashScreenDataSource
import nl.rhaydus.pokedex.core.domain.repository.SplashScreenRepository
import nl.rhaydus.pokedex.features.pokemon_display.domain.model.Pokemon
import timber.log.Timber
import javax.inject.Inject

class SplashScreenRepositoryImpl @Inject constructor(
    private val remoteSplashScreenDataSource: RemoteSplashScreenDataSource,
    private val localSplashScreenDataSource: LocalSplashScreenDataSource
) : SplashScreenRepository {
    override suspend fun initializeAllPokemon() {
        if (localSplashScreenDataSource.isLocalDataSourceComplete()) {
            Timber.d("Database was complete!")
            return
        }

        Timber.d("Database was not complete!")

        val pokemon: List<Pokemon> = remoteSplashScreenDataSource.initializeAllPokemon()
        Timber.d("Amount of pokemon which are being added: ${pokemon.size}")

        localSplashScreenDataSource.addPokemon(pokemon)
    }
}