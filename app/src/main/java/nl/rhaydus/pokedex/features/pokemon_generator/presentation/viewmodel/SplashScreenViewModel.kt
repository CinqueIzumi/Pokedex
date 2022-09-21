package nl.rhaydus.pokedex.features.pokemon_generator.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import nl.rhaydus.pokedex.core.DEBUG_TAG
import nl.rhaydus.pokedex.features.pokemon_generator.data.dao.PokemonDao
import nl.rhaydus.pokedex.features.pokemon_generator.data.mapper.toPokemonEntity
import nl.rhaydus.pokedex.features.pokemon_generator.domain.model.Pokemon
import nl.rhaydus.pokedex.features.pokemon_generator.domain.use_cases.GetAllPokemon
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class SplashScreenViewModel @Inject constructor(
    private val pokeDao: PokemonDao,
    private val getAllPokemonUseCase: GetAllPokemon,
) : ViewModel() {

    suspend fun initializePokemonInRoom() {
        withContext(Dispatchers.IO) {
            if (pokeDao.getAll().isEmpty()) {
                val result = getAllPokemonUseCase()

                result.fold(
                    onSuccess = { pokeList ->
                        Log.d(DEBUG_TAG, "Finished loading pokes!")

                        addToRoom(pokeList)

                        Log.d(DEBUG_TAG, "Finished adding pokes to room!")
                    },
                    onFailure = {
                        Log.e(DEBUG_TAG, it.message.toString())
                    }
                )
            }
        }
    }

    private fun addToRoom(pokeList: List<Pokemon>) {
        try {
            for (poke in pokeList) {
                pokeDao.insert(poke.toPokemonEntity())
            }
        } catch (e: Exception) {
            Log.e(DEBUG_TAG, e.message.toString())
        }
    }
}