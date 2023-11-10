package nl.rhaydus.pokedex.features.pokemon_display.presentation.uievent

import nl.rhaydus.pokedex.features.pokemon_display.domain.enums.PokemonSortEnum
import nl.rhaydus.pokedex.features.pokemon_display.domain.enums.PokemonTypeEnum

sealed class PokemonDisplayOverviewUiEvent {
    data object GetAllPokemon : PokemonDisplayOverviewUiEvent()
    data class UpdateFilterWithType(val givenType: PokemonTypeEnum?) : PokemonDisplayOverviewUiEvent()
    data class GetPokemonWithNameOrId(val nameOrId: String? = null) : PokemonDisplayOverviewUiEvent()
    data class UpdateListWithSort(val sortingType: PokemonSortEnum) : PokemonDisplayOverviewUiEvent()
}