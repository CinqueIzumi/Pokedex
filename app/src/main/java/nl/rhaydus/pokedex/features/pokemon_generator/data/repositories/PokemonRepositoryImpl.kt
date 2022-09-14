package nl.rhaydus.pokedex.features.pokemon_generator.data.repositories

import nl.rhaydus.pokedex.features.pokemon_generator.data.data_sources.RemotePokemonDataSource
import nl.rhaydus.pokedex.features.pokemon_generator.domain.model.Pokemon
import nl.rhaydus.pokedex.features.pokemon_generator.domain.repositories.PokemonRepository

class PokemonRepositoryImpl(
    private val remotePokemonDataSource: RemotePokemonDataSource
): PokemonRepository {
    override suspend fun getRandomPokemon(): Result<Pokemon> {
        // TODO: Implement connection checker?
        return kotlin.runCatching {
            remotePokemonDataSource.getRandomPokemonFromApi()
        }
    }
}