package nl.rhaydus.pokedex.features.pokemon_display.domain.enums

import nl.rhaydus.pokedex.R
import nl.rhaydus.pokedex.core.presentation.component.UiText

enum class PokemonSortEnum(val description: UiText) {
    NUMBER_ASCENDING(UiText.StringResource(R.string.sort_type_number_ascending)),
    NUMBER_DESCENDING(UiText.StringResource(R.string.sort_type_number_descending)),
    NAME_ASCENDING(UiText.StringResource(R.string.sort_type_name_ascending)),
    NAME_DESCENDING(UiText.StringResource(R.string.sort_type_name_descending))
}