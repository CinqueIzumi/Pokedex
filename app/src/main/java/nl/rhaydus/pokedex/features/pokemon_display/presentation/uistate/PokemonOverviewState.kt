package nl.rhaydus.pokedex.features.pokemon_display.presentation.uistate

import nl.rhaydus.pokedex.core.domain.model.Pokemon
import nl.rhaydus.pokedex.features.pokemon_display.domain.enums.PokemonSortEnum
import nl.rhaydus.pokedex.features.pokemon_display.domain.enums.PokemonTypeEnum

data class PokemonOverviewState(
    val pokemonList: List<Pokemon> = emptyList(),
    val isLoading: Boolean = false,
    val searchFilter: String? = null,
    val filteredType: PokemonTypeEnum? = null,
    val sortType: PokemonSortEnum? = PokemonSortEnum.NUMBER_ASCENDING,
)