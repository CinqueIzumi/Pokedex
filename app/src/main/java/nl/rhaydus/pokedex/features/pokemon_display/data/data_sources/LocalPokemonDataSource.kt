package nl.rhaydus.pokedex.features.pokemon_display.data.data_sources

import nl.rhaydus.pokedex.features.pokemon_display.domain.model.Pokemon

interface LocalPokemonDataSource {
    suspend fun getPokemonUntilId(id: Int): List<Pokemon>
    suspend fun getRandomPokemon(): Pokemon
    suspend fun getSpecificPokemon(pokemonId: Int): Pokemon
    suspend fun getAllPokemon(): List<Pokemon>
    suspend fun addPokemons(pokes: List<Pokemon>)
    suspend fun isLocalDataComplete(): Boolean
}