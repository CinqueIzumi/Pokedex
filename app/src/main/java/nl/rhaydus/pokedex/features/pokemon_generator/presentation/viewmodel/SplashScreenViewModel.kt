package nl.rhaydus.pokedex.features.pokemon_generator.presentation.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import nl.rhaydus.pokedex.features.pokemon_generator.data.dao.PokemonDao
import nl.rhaydus.pokedex.features.pokemon_generator.data.mapper.toPokemonEntity
import nl.rhaydus.pokedex.features.pokemon_generator.domain.model.Pokemon
import nl.rhaydus.pokedex.features.pokemon_generator.domain.use_cases.GetAllPokemon
import timber.log.Timber
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class SplashScreenViewModel @Inject constructor(
    private val getAllPokemonUseCase: GetAllPokemon,
) : ViewModel() {

    suspend fun initializePokemon() {
        withContext(Dispatchers.IO) {
            val result = getAllPokemonUseCase()

            result.fold(
                onSuccess = { pokeList ->
                    Timber.d("Finished loading pokes!\nSize: ${pokeList.size}")
                },
                onFailure = { error ->
                    Timber.e(error)
                }
            )
        }
    }
}