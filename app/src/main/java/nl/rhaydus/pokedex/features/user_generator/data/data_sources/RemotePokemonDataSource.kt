package nl.rhaydus.pokedex.features.user_generator.data.data_sources

import nl.rhaydus.pokedex.features.user_generator.domain.model.Pokemon

interface RemotePokemonDataSource {
    suspend fun getRandomPokemonFromApi(): Pokemon
}