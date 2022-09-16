package nl.rhaydus.pokedex.features.pokemon_generator.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import nl.rhaydus.pokedex.features.core.DEBUG_TAG
import nl.rhaydus.pokedex.features.pokemon_generator.domain.model.Pokemon
import nl.rhaydus.pokedex.features.pokemon_generator.domain.use_cases.GetRandomPokemon
import javax.inject.Inject

@HiltViewModel
class PokemonFragmentViewModel @Inject constructor(
    private val getRandomPokemonUseCase: GetRandomPokemon
) : ViewModel() {

    private val _currentPokemon = MutableLiveData<Pokemon?>()
    val currentPokemon: LiveData<Pokemon?> = _currentPokemon

    private val _loadingState = MutableLiveData(true)
    val loadingState: LiveData<Boolean> = _loadingState

    private val _errorState = MutableLiveData<String?>()
    val errorState: LiveData<String?> = _errorState

    suspend fun loadRandomPokemon() {
        _loadingState.postValue(true)

        val result = getRandomPokemonUseCase()

        result.fold(
            onSuccess = { pokemon ->
                _currentPokemon.postValue(pokemon)
            },
            onFailure = { error ->
                Log.e(DEBUG_TAG, error.toString())
                _errorState.postValue(error.message.toString())
            }
        )

        _loadingState.postValue(false)
    }
}