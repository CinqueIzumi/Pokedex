package nl.rhaydus.pokedex.features.pokemon_generator.domain.use_cases

import nl.rhaydus.pokedex.features.pokemon_generator.domain.model.Pokemon
import nl.rhaydus.pokedex.features.pokemon_generator.domain.repositories.PokemonRepository

class GetPokemonUntilId(private val repository: PokemonRepository) {
    suspend operator fun invoke(id: Int): Result<List<Pokemon>> {
        return repository.getPokemonUntilId(id)
    }
}