package nl.rhaydus.pokedex.features.pokemon_display.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import nl.rhaydus.pokedex.features.pokemon_display.domain.model.Pokemon
import nl.rhaydus.pokedex.features.pokemon_display.domain.use_cases.GetAllPokemon
import nl.rhaydus.pokedex.features.pokemon_display.domain.use_cases.GetPokemonWithFilter
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class PokemonScreenViewModel @Inject constructor(
    private val getPokemonWithFilterUseCase: GetPokemonWithFilter,
    private val getAllPokemonUseCase: GetAllPokemon
) : ViewModel() {

    private val _currentPokemonList = MutableLiveData<List<Pokemon>?>()
    val currentPokemonList: LiveData<List<Pokemon>?> = _currentPokemonList

    private val _loadingState = MutableLiveData(true)
    val loadingState: LiveData<Boolean> = _loadingState

    suspend fun applyFilter(
        givenQuery: String? = null,
        favorites: Boolean? = null,
        mainType: String? = null,
        secondaryType: String? = null
    ) {
        withContext(Dispatchers.IO) {
            _loadingState.postValue(true)

            val result = getPokemonWithFilterUseCase(
                nameOrId = givenQuery,
                isFavorite = favorites,
                mainType = mainType,
                secondaryType = secondaryType
            )

            result.fold(
                onSuccess = { pokeList ->
                    _currentPokemonList.postValue(pokeList)
                },
                onFailure = { error ->
                    Timber.e(error)
                })
            _loadingState.postValue(false)
        }
    }

    suspend fun initializePokemon() {
        withContext(Dispatchers.IO) {
            _loadingState.postValue(true)

            val result = getAllPokemonUseCase()

            result.fold(
                onSuccess = { pokeList ->
                    _currentPokemonList.postValue(pokeList)
                },
                onFailure = { error ->
                    Timber.e(error)
                }
            )

            _loadingState.postValue(false)
        }
    }
}