package nl.rhaydus.pokedex.features.pokemon_display.domain.usecase

import nl.rhaydus.pokedex.core.domain.model.Pokemon
import nl.rhaydus.pokedex.features.pokemon_display.domain.repositories.PokemonRepository
import javax.inject.Inject

class GetPokemonWithFilter @Inject constructor(
    private val pokemonRepository: PokemonRepository
) {
    suspend operator fun invoke(
        nameOrId: String?,
        isFavorite: Boolean?,
        mainType: String?
    ): Result<List<Pokemon>> = runCatching {
        pokemonRepository.getPokemonWithFilter(
            nameOrId = nameOrId,
            isFavorite = isFavorite,
            mainType = mainType
        )
    }
}