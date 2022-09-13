package com.example.myapplication.features.user_generator.domain.repositories

import com.example.myapplication.features.user_generator.domain.model.Pokemon

interface UserRepository {
    suspend fun getRandomPokemon(): Result<Pokemon>
}