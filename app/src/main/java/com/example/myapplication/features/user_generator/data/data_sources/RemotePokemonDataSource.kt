package com.example.myapplication.features.user_generator.data.data_sources

import com.example.myapplication.features.user_generator.domain.model.Pokemon

interface RemotePokemonDataSource {
    suspend fun getRandomPokemonFromApi(): Pokemon
}