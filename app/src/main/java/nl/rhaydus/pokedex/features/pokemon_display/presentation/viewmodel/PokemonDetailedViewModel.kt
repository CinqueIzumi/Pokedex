package nl.rhaydus.pokedex.features.pokemon_display.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import nl.rhaydus.pokedex.features.pokemon_display.domain.model.Pokemon
import nl.rhaydus.pokedex.features.pokemon_display.domain.usecase.GetSpecificPokemon
import nl.rhaydus.pokedex.features.pokemon_display.presentation.uievent.PokemonDisplayDetailedUiEvent
import nl.rhaydus.pokedex.features.pokemon_display.presentation.uistate.PokemonDetailedState
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class PokemonDetailedViewModel @Inject constructor(
    private val getSpecificPokemonUseCase: GetSpecificPokemon
) : ViewModel() {
    private val _pokemonDisplayScreenState = MutableStateFlow(PokemonDetailedState())
    val pokemonDisplayScreenState = _pokemonDisplayScreenState.asStateFlow()

    fun handleEvent(event: PokemonDisplayDetailedUiEvent) {
        when (event) {
            is PokemonDisplayDetailedUiEvent.InitializePokemon -> {
                val pokemonIsComplete = event.pokemon.isComplete()
                Timber.d("Pokemon was complete: $pokemonIsComplete")

                if (pokemonIsComplete) {
                    setCurrentPokemon(event.pokemon)
                } else {
                    getSpecificPokemon(event.pokemon.id)
                }
            }
        }
    }

    private fun getSpecificPokemon(id: Int) {
        Timber.d("Started loading pokemon...")
        viewModelScope.launch {
            setLoading(true)

            getSpecificPokemonUseCase(id).fold(
                onSuccess = { pokemon -> setCurrentPokemon(pokemon) },
                onFailure = { Timber.e("Something went wrong!") }
            )

            setLoading(false)
        }
    }

    private fun setLoading(isLoading: Boolean) {
        _pokemonDisplayScreenState.update {
            it.copy(isLoading = isLoading)
        }
    }

    private fun setCurrentPokemon(poke: Pokemon) {
        _pokemonDisplayScreenState.update {
            it.copy(pokemon = poke)
        }
    }
}