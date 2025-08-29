package nl.rhaydus.pokedex.feature.type.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import nl.rhaydus.pokedex.feature.type.domain.model.PokemonType
import nl.rhaydus.pokedex.feature.type.presentation.event.TypeScreenUiEvent
import nl.rhaydus.pokedex.feature.type.presentation.state.TypeScreenUiState
import javax.inject.Inject

class TypeScreenViewModel @Inject constructor() : ViewModel() {
    private val _searchTextFlow = MutableStateFlow("")

    val state: StateFlow<TypeScreenUiState> = _searchTextFlow.map { searchText: String ->
        val filteredTypes = PokemonType
            .entries
            .filter { type: PokemonType ->
                type.name.contains(
                    other = searchText,
                    ignoreCase = true,
                )
            }

        TypeScreenUiState(
            searchText = searchText,
            types = filteredTypes
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Companion.WhileSubscribed(5_000),
        initialValue = TypeScreenUiState()
    )

    fun onEvent(event: TypeScreenUiEvent) {
        when (event) {
            is TypeScreenUiEvent.OnSearchTextChanged -> {
                handleOnSearchTextChanged(newValue = event.newValue)
            }
        }
    }

    // region Event handling
    private fun handleOnSearchTextChanged(newValue: String) = setSearchText(value = newValue)
    // endregion

    // region Helper functions
    private fun setSearchText(value: String) = _searchTextFlow.update { value }
    // endregion
}