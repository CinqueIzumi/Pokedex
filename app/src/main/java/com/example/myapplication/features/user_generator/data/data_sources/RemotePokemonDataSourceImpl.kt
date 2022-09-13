package com.example.myapplication.features.user_generator.data.data_sources

import com.example.myapplication.features.core.EmptyUser
import com.example.myapplication.features.user_generator.data.mapper.toPokemon
import com.example.myapplication.features.user_generator.data.network.PokemonApiService
import com.example.myapplication.features.user_generator.domain.model.Pokemon

class RemotePokemonDataSourceImpl : RemotePokemonDataSource {
    private val service: PokemonApiService = PokemonApiService.getPokemonApiService()

    override suspend fun getRandomPokemonFromApi(): Pokemon {
        val randomId = (0..900).shuffled().last()

        val response = service.getRandomPokemon(randomId)

        return response.body()?.toPokemon() ?: throw EmptyUser()
    }
}