package nl.rhaydus.pokedex.features.pokemon_generator.data.data_sources

import nl.rhaydus.pokedex.features.pokemon_generator.domain.model.Pokemon

interface RemotePokemonDataSource {
    suspend fun getRandomPokemonFromApi(): Pokemon
    suspend fun getSpecificPokemonFromApi(pokemonId: Int): Pokemon
    suspend fun getAllPokemon(): List<Pokemon>
    suspend fun getPokemonUntilId(id: Int): List<Pokemon>
}