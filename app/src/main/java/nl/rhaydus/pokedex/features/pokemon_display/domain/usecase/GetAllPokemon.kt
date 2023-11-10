package nl.rhaydus.pokedex.features.pokemon_display.domain.usecase

import nl.rhaydus.pokedex.features.pokemon_display.domain.model.Pokemon
import nl.rhaydus.pokedex.features.pokemon_display.domain.repositories.PokemonRepository
import javax.inject.Inject

class GetAllPokemon @Inject constructor(
    private val pokemonRepository: PokemonRepository
) {
    suspend operator fun invoke(): Result<List<Pokemon>> = runCatching {
        pokemonRepository.getAllPokemon()
    }
}