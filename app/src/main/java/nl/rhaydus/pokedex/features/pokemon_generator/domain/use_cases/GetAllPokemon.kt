package nl.rhaydus.pokedex.features.pokemon_generator.domain.use_cases

import nl.rhaydus.pokedex.features.pokemon_generator.domain.model.Pokemon
import nl.rhaydus.pokedex.features.pokemon_generator.domain.repositories.PokemonRepository

class GetAllPokemon(private val repository: PokemonRepository) {
    suspend operator fun invoke(): Result<List<Pokemon>> {
        return repository.getAllPokemon()
    }
}