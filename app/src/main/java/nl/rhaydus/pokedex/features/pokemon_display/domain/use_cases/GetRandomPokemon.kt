package nl.rhaydus.pokedex.features.pokemon_display.domain.use_cases

import nl.rhaydus.pokedex.features.pokemon_display.domain.model.Pokemon
import nl.rhaydus.pokedex.features.pokemon_display.domain.repositories.PokemonRepository

class GetRandomPokemon(private val repository: PokemonRepository) {
    suspend operator fun invoke(): Result<Pokemon> = repository.getRandomPokemon()
}