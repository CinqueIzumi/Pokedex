package nl.rhaydus.pokedex.features.pokemon_display.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import nl.rhaydus.pokedex.features.pokemon_display.domain.use_cases.GetAllPokemon
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SplashScreenViewModel @Inject constructor(
    private val getAllPokemonUseCase: GetAllPokemon,
) : ViewModel() {

    private val _finishedLoading = MutableLiveData(false)
    val finishedLoading = _finishedLoading

    suspend fun initializePokemon() {
        withContext(Dispatchers.IO) {
            val result = getAllPokemonUseCase()
            result.fold(
                onSuccess = { pokeList ->
                    Timber.d("Finished loading pokes!\nSize: ${pokeList.size}")
                    _finishedLoading.postValue(true)
                },
                onFailure = { error -> Timber.e(error) }
            )
        }
    }
}