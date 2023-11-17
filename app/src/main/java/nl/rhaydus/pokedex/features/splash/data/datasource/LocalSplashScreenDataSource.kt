package nl.rhaydus.pokedex.features.splash.data.datasource

import nl.rhaydus.pokedex.core.domain.model.Pokemon

interface LocalSplashScreenDataSource {
    suspend fun addPokemon(pokemon: List<Pokemon>)
    suspend fun isLocalDataSourceComplete(): Boolean
}