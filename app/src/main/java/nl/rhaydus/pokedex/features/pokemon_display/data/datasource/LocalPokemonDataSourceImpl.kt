package nl.rhaydus.pokedex.features.pokemon_display.data.datasource

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import nl.rhaydus.pokedex.core.data.mapper.toPokemon
import nl.rhaydus.pokedex.core.data.mapper.toPokemonEntity
import nl.rhaydus.pokedex.core.domain.model.Pokemon
import nl.rhaydus.pokedex.features.pokemon_display.data.dao.PokemonDao
import nl.rhaydus.pokedex.features.pokemon_display.data.helper.QueryHelper
import nl.rhaydus.pokedex.features.pokemon_display.data.model.PokemonEntity
import timber.log.Timber
import javax.inject.Inject

class LocalPokemonDataSourceImpl @Inject constructor(
    private val pokemonDao: PokemonDao
) : LocalPokemonDataSource {
    override suspend fun getAllPokemon(): List<Pokemon> {
        val pokemonList: List<Pokemon> = withContext(Dispatchers.IO) {
            pokemonDao.getAll().map { pokemonEntity: PokemonEntity -> pokemonEntity.toPokemon() }
        }

        Timber.d("Pokemon list found with size: ${pokemonList.size}")
        return pokemonList
    }

    override suspend fun addPokemon(pokemon: Pokemon) {
        withContext(Dispatchers.IO) {
            pokemonDao.updatePokemon(pokemon.toPokemonEntity())
        }

        Timber.d("Pokemon with ID ${pokemon.id} has been added!")
    }

    override suspend fun getPokemonWithFilter(
        nameOrId: String?,
        isFavorite: Boolean?,
        mainType: String?
    ): List<Pokemon> {
        val query =
            QueryHelper.getQuery(nameOrId = nameOrId, mainType = mainType, isFavorite = isFavorite)
        Timber.d("Query string: ${query.sql}")

        return withContext(Dispatchers.IO) {
            pokemonDao.getFilteredPokemons(query).map { it.toPokemon() }
        }
    }
}