package nl.rhaydus.pokedex.features.pokemon_generator.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import nl.rhaydus.pokedex.features.pokemon_generator.data.dao.PokemonDao
import nl.rhaydus.pokedex.features.pokemon_generator.data.mapper.toPokemon
import nl.rhaydus.pokedex.features.pokemon_generator.data.model.PokemonEntity
import nl.rhaydus.pokedex.features.pokemon_generator.domain.model.Pokemon
import timber.log.Timber
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class PokemonScreenViewModel @Inject constructor(
    private val pokeDao: PokemonDao,
) : ViewModel() {

    private val _currentPokemonList = MutableLiveData<List<Pokemon>?>()
    val currentPokemonList: LiveData<List<Pokemon>?> = _currentPokemonList

    private val _loadingState = MutableLiveData(true)
    val loadingState: LiveData<Boolean> = _loadingState

    private val _errorState = MutableLiveData<String?>()
    val errorState: LiveData<String?> = _errorState

    suspend fun applyFilter(filter: String) {
        withContext(Dispatchers.IO) {
            _loadingState.postValue(true)
            try {
                val pokeEntityList: List<PokemonEntity> = pokeDao.getSpecificPokemon(filter)
                Timber.d("List has been initialized! $pokeEntityList")
                initializeList(pokeEntityList)
            } catch (e: Exception) {
                _errorState.postValue(e.message)
                Timber.e(e)
            }

            _loadingState.postValue(false)
        }
    }

    private fun initializeList(givenEntityList: List<PokemonEntity>) {
        val pokemonList = mutableListOf<Pokemon>()

        for (pokeEntity in givenEntityList) {
            pokemonList.add(pokeEntity.toPokemon())
        }

        _currentPokemonList.postValue(pokemonList)
    }

    suspend fun getPokemonFromRoom() {
        withContext(Dispatchers.IO) {
            _loadingState.postValue(true)

            try {
                val pokeEntityList: List<PokemonEntity> = pokeDao.getAll()
                initializeList(pokeEntityList)
            } catch (e: Exception) {
                _errorState.postValue(e.message)
            }

            _loadingState.postValue(false)
        }
    }
}