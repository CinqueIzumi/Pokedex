package nl.rhaydus.pokedex.feature.type.presentation.state

import nl.rhaydus.pokedex.feature.type.domain.model.PokemonType

data class TypeScreenUiState(
    val searchText: String = "",
    val types: List<PokemonType> = PokemonType.entries,
)