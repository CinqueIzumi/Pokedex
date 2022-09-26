package nl.rhaydus.pokedex.features.pokemon_display.domain.repositories

import nl.rhaydus.pokedex.features.pokemon_display.domain.model.Pokemon

interface PokemonRepository {
    suspend fun getRandomPokemon(): Result<Pokemon>
    suspend fun getSpecificPokemon(pokemonId: Int): Result<Pokemon>
    suspend fun getAllPokemon(): Result<List<Pokemon>>
    suspend fun getPokemonUntilId(id: Int): Result<List<Pokemon>>
}