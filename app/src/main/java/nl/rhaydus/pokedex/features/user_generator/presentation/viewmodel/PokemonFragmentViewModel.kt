package nl.rhaydus.pokedex.features.user_generator.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import nl.rhaydus.pokedex.features.user_generator.data.data_sources.RemotePokemonDataSourceImpl
import nl.rhaydus.pokedex.features.user_generator.data.repositories.PokemonRepositoryImpl
import nl.rhaydus.pokedex.features.user_generator.domain.model.Pokemon
import nl.rhaydus.pokedex.features.user_generator.domain.use_cases.GenerateRandomPokemon

class PokemonFragmentViewModel : ViewModel() {
    private val remotePokemonDataSource = RemotePokemonDataSourceImpl()
    private val pokemonRepositoryImpl = PokemonRepositoryImpl(remotePokemonDataSource)
    private val getRandomPokemonUseCase = GenerateRandomPokemon(pokemonRepositoryImpl)

    private val _currentPokemon = MutableLiveData<Pokemon>()
    fun getCurrentPokemon(): LiveData<Pokemon> = _currentPokemon

    suspend fun loadRandomPokemon() {
        val result = getRandomPokemonUseCase()

        result?.let { pokemon -> _currentPokemon.postValue(pokemon) }
    }
}