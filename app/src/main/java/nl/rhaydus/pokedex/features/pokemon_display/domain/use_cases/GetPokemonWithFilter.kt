package nl.rhaydus.pokedex.features.pokemon_display.domain.use_cases

import nl.rhaydus.pokedex.features.pokemon_display.domain.model.Pokemon
import nl.rhaydus.pokedex.features.pokemon_display.domain.repositories.PokemonRepository

class GetPokemonWithFilter(private val repository: PokemonRepository) {
    suspend operator fun invoke(
        nameOrId: String? = null,
        isFavorite: Boolean? = null,
        mainType: String? = null,
        secondaryType: String? = null
    ): Result<List<Pokemon>> =
        repository.getPokemonWithFilter(
            nameOrId = nameOrId,
            isFavorite = isFavorite,
            mainType = mainType,
            secondaryType = secondaryType
        )
}