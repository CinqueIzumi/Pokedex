package nl.rhaydus.pokedex.features.pokemon_generator.data.data_sources

import nl.rhaydus.pokedex.features.pokemon_generator.domain.model.Pokemon

interface RemotePokemonDataSource {
    suspend fun getRandomPokemonFromApi(): Pokemon
}