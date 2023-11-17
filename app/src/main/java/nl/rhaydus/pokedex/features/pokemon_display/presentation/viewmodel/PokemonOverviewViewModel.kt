package nl.rhaydus.pokedex.features.pokemon_display.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import nl.rhaydus.pokedex.core.domain.model.Pokemon
import nl.rhaydus.pokedex.core.domain.model.handlePokemonOrder
import nl.rhaydus.pokedex.features.pokemon_display.domain.enums.PokemonTypeEnum
import nl.rhaydus.pokedex.features.pokemon_display.domain.usecase.GetAllPokemon
import nl.rhaydus.pokedex.features.pokemon_display.domain.usecase.GetPokemonWithFilter
import nl.rhaydus.pokedex.features.pokemon_display.presentation.uievent.PokemonDisplayOverviewUiEvent
import nl.rhaydus.pokedex.features.pokemon_display.presentation.uistate.PokemonOverviewState
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class PokemonOverviewViewModel @Inject constructor(
    private val getAllPokemonUseCase: GetAllPokemon,
    private val getPokemonWithFilterUseCase: GetPokemonWithFilter
) : ViewModel() {
    private val _pokemonDisplayScreenState = MutableStateFlow(PokemonOverviewState())
    val pokemonDisplayScreenState = _pokemonDisplayScreenState.asStateFlow()

    fun handleEvent(event: PokemonDisplayOverviewUiEvent) {
        when (event) {
            is PokemonDisplayOverviewUiEvent.GetAllPokemon -> {
                getAllPokemon()
            }

            is PokemonDisplayOverviewUiEvent.UpdateFilterWithType -> {
                getPokemonWithFilter(
                    mainType = event.givenType,
                    isFavorite = null,
                    nameOrId = _pokemonDisplayScreenState.value.searchFilter
                )
            }

            is PokemonDisplayOverviewUiEvent.UpdateListWithSort -> {
                val newPokemonList: List<Pokemon> =
                    _pokemonDisplayScreenState.value.pokemonList.handlePokemonOrder(event.sortingType)

                _pokemonDisplayScreenState.update {
                    it.copy(pokemonList = newPokemonList, sortType = event.sortingType)
                }
            }

            is PokemonDisplayOverviewUiEvent.GetPokemonWithNameOrId -> getPokemonWithFilter(
                nameOrId = event.nameOrId,
                isFavorite = null,
                mainType = _pokemonDisplayScreenState.value.filteredType
            )
        }
    }

    private fun getPokemonWithFilter(
        nameOrId: String?,
        isFavorite: Boolean?,
        mainType: PokemonTypeEnum?,
    ) {
        Timber.d("Started getting pokemon!")
        viewModelScope.launch {
            setLoading(true)

            getPokemonWithFilterUseCase(
                nameOrId,
                isFavorite,
                mainType?.name,
            ).fold(
                onSuccess = { pokeList ->
                    val sortedList = sortListWithCurrentSort(pokeList)
                    _pokemonDisplayScreenState.update {
                        it.copy(
                            pokemonList = sortedList,
                            filteredType = mainType,
                            searchFilter = nameOrId
                        )
                    }
                },
                onFailure = { Timber.e(it) }
            )

            setLoading(false)
        }
    }

    private fun getAllPokemon() {
        Timber.d("Started getting pokemon!")
        viewModelScope.launch {
            setLoading(true)

            getAllPokemonUseCase().fold(
                onSuccess = { pokeList ->
                    val sortedList = sortListWithCurrentSort(pokeList)
                    _pokemonDisplayScreenState.update {
                        it.copy(pokemonList = sortedList)
                    }
                },
                onFailure = { Timber.e(it) }
            )

            setLoading(false)
        }
    }

    private fun setLoading(isLoading: Boolean) {
        _pokemonDisplayScreenState.update {
            it.copy(isLoading = isLoading)
        }
    }

    private fun sortListWithCurrentSort(pokemonList: List<Pokemon>): List<Pokemon> =
        pokemonList.handlePokemonOrder(_pokemonDisplayScreenState.value.sortType)
}