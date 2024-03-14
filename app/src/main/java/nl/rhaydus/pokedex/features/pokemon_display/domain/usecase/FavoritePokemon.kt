package nl.rhaydus.pokedex.features.pokemon_display.domain.usecase

import nl.rhaydus.pokedex.core.domain.model.Pokemon
import nl.rhaydus.pokedex.features.pokemon_display.domain.repositories.PokemonRepository
import javax.inject.Inject

class FavoritePokemon @Inject constructor(private val pokemonRepository: PokemonRepository) {
    suspend operator fun invoke(pokemon: Pokemon): Result<Unit> =
        runCatching { pokemonRepository.favoritePokemon(pokemon) }
}