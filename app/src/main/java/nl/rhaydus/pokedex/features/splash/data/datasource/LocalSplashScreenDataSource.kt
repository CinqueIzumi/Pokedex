package nl.rhaydus.pokedex.features.splash.data.datasource

import nl.rhaydus.pokedex.features.pokemon_display.domain.model.Pokemon

interface LocalSplashScreenDataSource {
    suspend fun addPokemon(pokemon: List<Pokemon>)
    suspend fun isLocalDataSourceComplete(): Boolean
}