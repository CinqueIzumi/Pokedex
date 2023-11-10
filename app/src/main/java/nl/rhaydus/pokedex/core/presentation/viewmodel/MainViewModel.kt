package nl.rhaydus.pokedex.core.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import nl.rhaydus.pokedex.features.splash.domain.usecase.InitializeAllPokemon
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    val initializeAllPokemonUseCase: InitializeAllPokemon
) : ViewModel() {
    private val _showSplashScreen = MutableStateFlow(true)
    val showSplashScreen = _showSplashScreen.asStateFlow()

    fun initialize() {
        viewModelScope.launch {
            initializeAllPokemonUseCase()
            Timber.d("Finished initializing")
            _showSplashScreen.value = false
        }
    }
}