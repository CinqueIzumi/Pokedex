package nl.rhaydus.pokedex.features.user_generator.domain.repositories

import nl.rhaydus.pokedex.features.user_generator.domain.model.Pokemon

interface UserRepository {
    suspend fun getRandomPokemon(): Result<Pokemon>
}