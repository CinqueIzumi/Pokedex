package nl.rhaydus.pokedex.features.pokemon_display.data.repositories

import nl.rhaydus.pokedex.features.pokemon_display.data.data_sources.LocalPokemonDataSource
import nl.rhaydus.pokedex.features.pokemon_display.data.data_sources.RemotePokemonDataSource
import nl.rhaydus.pokedex.features.pokemon_display.domain.model.Pokemon
import nl.rhaydus.pokedex.features.pokemon_display.domain.repositories.PokemonRepository
import timber.log.Timber

class PokemonRepositoryImpl(
    private val remotePokemonDataSource: RemotePokemonDataSource,
    private val localPokemonDataSource: LocalPokemonDataSource,
) : PokemonRepository {
    override suspend fun getRandomPokemon(): Result<Pokemon> =
        runCatching { localPokemonDataSource.getRandomPokemon() }

    override suspend fun getSpecificPokemon(pokemonId: Int): Result<Pokemon> =
        runCatching { localPokemonDataSource.getSpecificPokemon(pokemonId) }

    override suspend fun getAllPokemon(): Result<List<Pokemon>> {
        return runCatching {
            if (!localPokemonDataSource.isLocalDataComplete()) {
                Timber.d("Pokemon data source was not complete!")
                localPokemonDataSource.addPokemons(remotePokemonDataSource.getAllPokemon())
            }

            localPokemonDataSource.getAllPokemon()
        }
    }

    override suspend fun getPokemonUntilId(id: Int): Result<List<Pokemon>> =
        kotlin.runCatching { localPokemonDataSource.getPokemonUntilId(id) }
}