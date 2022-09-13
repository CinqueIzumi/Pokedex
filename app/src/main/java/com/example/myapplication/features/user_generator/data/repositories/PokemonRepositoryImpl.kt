package com.example.myapplication.features.user_generator.data.repositories

import com.example.myapplication.features.user_generator.data.data_sources.RemotePokemonDataSource
import com.example.myapplication.features.user_generator.domain.model.Pokemon
import com.example.myapplication.features.user_generator.domain.repositories.UserRepository

class PokemonRepositoryImpl(
    private val remotePokemonDataSource: RemotePokemonDataSource
): UserRepository {
    override suspend fun getRandomPokemon(): Result<Pokemon> {
        // TODO: Implement connection checker?
        return kotlin.runCatching {
            remotePokemonDataSource.getRandomPokemonFromApi()
        }
    }
}