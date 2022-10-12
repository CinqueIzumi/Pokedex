package nl.rhaydus.pokedex.features.pokemon_display.domain.use_cases

import nl.rhaydus.pokedex.features.pokemon_display.domain.model.Pokemon
import nl.rhaydus.pokedex.features.pokemon_display.domain.repositories.PokemonRepository

class GetPokemonUntilId(private val repository: PokemonRepository) {
    suspend operator fun invoke(id: Int): Result<List<Pokemon>> =
        repository.getPokemonUntilId(id = id)
}