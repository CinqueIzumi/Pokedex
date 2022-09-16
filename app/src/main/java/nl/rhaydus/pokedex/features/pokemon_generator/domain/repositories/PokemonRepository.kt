package nl.rhaydus.pokedex.features.pokemon_generator.domain.repositories

import nl.rhaydus.pokedex.features.pokemon_generator.domain.model.Pokemon

interface PokemonRepository {
    suspend fun getRandomPokemon(): Result<Pokemon>
    suspend fun getSpecificPokemon(pokemonId: Int): Result<Pokemon>
}