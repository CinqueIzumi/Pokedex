package nl.rhaydus.pokedex.features.pokemon_display.data.datasource

import androidx.sqlite.db.SimpleSQLiteQuery
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import nl.rhaydus.pokedex.core.data.mapper.toPokemon
import nl.rhaydus.pokedex.core.data.mapper.toPokemonEntity
import nl.rhaydus.pokedex.features.pokemon_display.data.dao.PokemonDao
import nl.rhaydus.pokedex.features.pokemon_display.data.model.PokemonEntity
import nl.rhaydus.pokedex.features.pokemon_display.domain.model.Pokemon
import timber.log.Timber
import javax.inject.Inject

class LocalPokemonDataSourceImpl @Inject constructor(
    private val pokemonDao: PokemonDao
) : LocalPokemonDataSource {
    private var containsConditions = false

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
        var queryString = ""
        val args = mutableListOf<Any>()

        // Set the contain conditions to false when a new query should be built
        containsConditions = false

        // Start the assembly of the query string
        queryString += "SELECT * FROM pokemonentity"

        // Add the search filter if required
        nameOrId?.let { givenNameOrId ->
            // Check whether the given query is numerical (id) or a pokemon name
            when (givenNameOrId.toIntOrNull()) {
                null -> {
                    queryString += addCondition() + " poke_name LIKE ?"
                    args.add("%$givenNameOrId%")
                }

                else -> {
                    queryString += addCondition() + " id = ?"
                    args.add(givenNameOrId)
                }
            }

            containsConditions = true
        }

        // Add the favorites filter if required
        isFavorite?.let { fav ->
            queryString += addCondition() + " favorite = ?"
            args.add(fav)
        }

        // Add the main type filter if required
        mainType?.let { givenMainType ->
            if (givenMainType != "All") {
                queryString += addCondition() + " poke_main_type = ?"
                args.add(givenMainType)
            }
        }

        // End of the query
        queryString += ";"

        Timber.d(queryString)

        // Perform the query
        val query = SimpleSQLiteQuery(queryString, args.toList().toTypedArray())
        Timber.d("Query string: ${query.sql}")
        return withContext(Dispatchers.IO) {
            pokemonDao.getFilteredPokemons(query).map { it.toPokemon() }
        }
    }

    private fun addCondition(): String {
        return if (containsConditions) {
            " AND"
        } else {
            containsConditions = true
            " WHERE"
        }
    }
}