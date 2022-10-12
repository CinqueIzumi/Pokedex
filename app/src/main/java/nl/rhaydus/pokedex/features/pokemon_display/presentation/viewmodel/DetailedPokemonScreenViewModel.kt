package nl.rhaydus.pokedex.features.pokemon_display.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import nl.rhaydus.pokedex.features.pokemon_display.domain.model.Pokemon
import nl.rhaydus.pokedex.features.pokemon_display.domain.use_cases.FavoritePokemon
import nl.rhaydus.pokedex.features.pokemon_display.domain.use_cases.UnFavoritePokemon
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class DetailedPokemonScreenViewModel @Inject constructor(
    private val favoritePokemonUseCase: FavoritePokemon,
    private val unFavoritePokemonUseCase: UnFavoritePokemon
) : ViewModel() {

    private val _loadingState = MutableLiveData(false)
    val loadingState: LiveData<Boolean> = _loadingState

    private val _isFavoriteState = MutableLiveData(false)
    val isFavoriteState: LiveData<Boolean> = _isFavoriteState

    fun checkIfFavorite(pokemon: Pokemon) {
        _loadingState.postValue(true)

        if (pokemon.favorite == 1) {
            _isFavoriteState.postValue(true)
        } else {
            _isFavoriteState.postValue(false)
        }

        Timber.d("Pokemon is a favorite: ${_isFavoriteState.value}")

        _loadingState.postValue(false)
    }

    suspend fun unFavoritePokemon(pokemon: Pokemon) {
        withContext(Dispatchers.IO) {
            _loadingState.postValue(true)

            val result = unFavoritePokemonUseCase(pokemon = pokemon)

            result.fold(
                onSuccess = {
                    _isFavoriteState.postValue(false)
                    Timber.d("Pokemon has been removed from favorites!")
                },
                onFailure = { error ->
                    Timber.e(error)
                }
            )
            _loadingState.postValue(false)
        }
    }

    suspend fun favoritePokemon(pokemon: Pokemon) {
        withContext(Dispatchers.IO) {
            _loadingState.postValue(true)

            val result = favoritePokemonUseCase(pokemon = pokemon)

            result.fold(
                onSuccess = {
                    _isFavoriteState.postValue(true)
                    Timber.d("Pokemon has been added to fav!")
                },
                onFailure = { error ->
                    Timber.e(error)
                }
            )

            _loadingState.postValue(false)
        }
    }
}