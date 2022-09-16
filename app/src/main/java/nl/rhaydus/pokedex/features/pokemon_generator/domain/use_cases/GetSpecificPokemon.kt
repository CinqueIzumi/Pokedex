package nl.rhaydus.pokedex.features.pokemon_generator.domain.use_cases

import nl.rhaydus.pokedex.features.pokemon_generator.domain.model.Pokemon
import nl.rhaydus.pokedex.features.pokemon_generator.domain.repositories.PokemonRepository

class GetSpecificPokemon(private val repository: PokemonRepository) {
    suspend operator fun invoke(pokemonId: Int): Result<Pokemon> {
        return repository.getSpecificPokemon(pokemonId)
    }
}