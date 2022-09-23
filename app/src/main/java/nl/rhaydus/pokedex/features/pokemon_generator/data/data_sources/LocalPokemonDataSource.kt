package nl.rhaydus.pokedex.features.pokemon_generator.data.data_sources

import nl.rhaydus.pokedex.features.pokemon_generator.domain.model.Pokemon

interface LocalPokemonDataSource {
    suspend fun getPokemonUntilId(id: Int): List<Pokemon>
    suspend fun getRandomPokemon(): Pokemon
    suspend fun getSpecificPokemon(pokemonId: Int): Pokemon
    suspend fun getAllPokemon(): List<Pokemon>
    suspend fun addPokemons(pokes: List<Pokemon>)
}