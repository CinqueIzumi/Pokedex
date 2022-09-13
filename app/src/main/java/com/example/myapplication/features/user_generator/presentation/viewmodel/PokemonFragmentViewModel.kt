package com.example.myapplication.features.user_generator.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.features.user_generator.data.data_sources.RemotePokemonDataSourceImpl
import com.example.myapplication.features.user_generator.data.repositories.PokemonRepositoryImpl
import com.example.myapplication.features.user_generator.domain.model.Pokemon
import com.example.myapplication.features.user_generator.domain.use_cases.GenerateRandomPokemon

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