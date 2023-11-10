package nl.rhaydus.pokedex.features.pokemon_display.data.repositories

import nl.rhaydus.pokedex.features.pokemon_display.data.datasource.LocalPokemonDataSource
import nl.rhaydus.pokedex.features.pokemon_display.data.datasource.RemotePokemonDataSource
import nl.rhaydus.pokedex.features.pokemon_display.domain.model.Pokemon
import nl.rhaydus.pokedex.features.pokemon_display.domain.repositories.PokemonRepository
import javax.inject.Inject

class PokemonRepositoryImpl @Inject constructor(
    private val remotePokemonDataSource: RemotePokemonDataSource,
    private val localPokemonDataSource: LocalPokemonDataSource,
) : PokemonRepository {
    override suspend fun getSpecificPokemon(id: Int): Pokemon {
        val poke = remotePokemonDataSource.getSpecificPokemon(id)
        localPokemonDataSource.addPokemon(poke)

        return poke
    }

    override suspend fun getAllPokemon(): List<Pokemon> = localPokemonDataSource.getAllPokemon()

    override suspend fun getPokemonWithFilter(
        nameOrId: String?,
        isFavorite: Boolean?,
        mainType: String?,
    ): List<Pokemon> = localPokemonDataSource.getPokemonWithFilter(
        nameOrId,
        isFavorite,
        mainType,
    )
}