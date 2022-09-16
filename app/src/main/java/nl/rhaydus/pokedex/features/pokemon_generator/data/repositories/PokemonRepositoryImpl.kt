package nl.rhaydus.pokedex.features.pokemon_generator.data.repositories

import nl.rhaydus.pokedex.features.pokemon_generator.data.data_sources.RemotePokemonDataSource
import nl.rhaydus.pokedex.features.pokemon_generator.domain.model.Pokemon
import nl.rhaydus.pokedex.features.pokemon_generator.domain.repositories.PokemonRepository

class PokemonRepositoryImpl(
    private val remotePokemonDataSource: RemotePokemonDataSource
) : PokemonRepository {
    override suspend fun getRandomPokemon(): Result<Pokemon> {
        // TODO: Implement connection checker?
        return runCatching {
            remotePokemonDataSource.getRandomPokemonFromApi()
        }
    }

    override suspend fun getSpecificPokemon(pokemonId: Int): Result<Pokemon> {
        return runCatching {
            remotePokemonDataSource.getSpecificPokemonFromApi(pokemonId)
        }
    }
}