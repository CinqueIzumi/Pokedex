package nl.rhaydus.pokedex.features.pokemon_display.domain.usecase

import nl.rhaydus.pokedex.core.domain.model.Pokemon
import nl.rhaydus.pokedex.features.pokemon_display.domain.repositories.PokemonRepository
import javax.inject.Inject

class GetSpecificPokemon @Inject constructor(
    private val pokemonRepository: PokemonRepository
) {
    suspend operator fun invoke(id: Int): Result<Pokemon> = runCatching {
        pokemonRepository.getSpecificPokemon(id = id)
    }
}