package nl.rhaydus.pokedex.feature.type.presentation.event

sealed class TypeScreenUiEvent {
    data class OnSearchTextChanged(val newValue: String) : TypeScreenUiEvent()
}