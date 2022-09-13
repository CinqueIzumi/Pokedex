package nl.rhaydus.pokedex.features.user_generator.data.repositories

import nl.rhaydus.pokedex.features.user_generator.data.data_sources.RemotePokemonDataSource
import nl.rhaydus.pokedex.features.user_generator.domain.model.Pokemon
import nl.rhaydus.pokedex.features.user_generator.domain.repositories.UserRepository

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