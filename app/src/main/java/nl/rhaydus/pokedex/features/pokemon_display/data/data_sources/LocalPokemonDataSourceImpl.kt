package nl.rhaydus.pokedex.features.pokemon_display.data.data_sources

import android.content.Context
import androidx.sqlite.db.SimpleSQLiteQuery
import dagger.hilt.android.qualifiers.ApplicationContext
import nl.rhaydus.pokedex.R
import nl.rhaydus.pokedex.features.pokemon_display.data.dao.PokemonDao
import nl.rhaydus.pokedex.features.pokemon_display.data.mapper.toPokemon
import nl.rhaydus.pokedex.features.pokemon_display.data.mapper.toPokemonEntity
import nl.rhaydus.pokedex.features.pokemon_display.data.mapper.toPokemonList
import nl.rhaydus.pokedex.features.pokemon_display.domain.model.Pokemon
import timber.log.Timber
import javax.inject.Inject

class LocalPokemonDataSourceImpl @Inject constructor(
    private val pokemonDao: PokemonDao,
    @ApplicationContext private val context: Context
) : LocalPokemonDataSource {
    override suspend fun getAllPokemon(): List<Pokemon> =
        pokemonDao.getAll().map { pokeEntity -> pokeEntity.toPokemon() }

    override suspend fun addPokemons(pokes: List<Pokemon>) {
        pokes.forEach { poke ->
            pokemonDao.insert(poke.toPokemonEntity())
        }
    }

    override suspend fun isLocalDataComplete(): Boolean =
        pokemonDao.getDatabaseSize() == context.resources.getInteger(R.integer.highest_pokemon_id)

    private fun updatePokemon(pokemon: Pokemon, favorite: Int) {
        pokemonDao.updatePokemon(pokemon.copy(favorite = favorite).toPokemonEntity())
    }

    override suspend fun favoritePokemon(pokemon: Pokemon) =
        updatePokemon(pokemon, 1)

    override suspend fun unFavoritePokemon(pokemon: Pokemon) =
        updatePokemon(pokemon, 0)

    override suspend fun getPokemonWithFilter(
        nameOrId: String?,
        isFavorite: Boolean?,
        mainType: String?,
        secondaryType: String?
    ): List<Pokemon> {
        var queryString = ""
        val args: MutableList<Any> = mutableListOf()
        var containsConditions = false

        // Start the assembly of the query string
        queryString += "SELECT * FROM pokemonentity"

        // Add the search filter if required
        nameOrId?.let { givenNameOrId ->
            // Check whether the given query is numerical (id) or a pokemon name
            when (givenNameOrId.toIntOrNull()) {
                null -> {
                    queryString += " WHERE poke_name LIKE ?"
                    args.add("%$givenNameOrId%")
                }
                else -> {
                    queryString += " WHERE id = ?"
                    args.add(givenNameOrId)
                }
            }

            containsConditions = true
        }

        // Add the favorites filter if required
        isFavorite?.let { fav ->
            if (containsConditions) {
                queryString += " AND"
            } else {
                queryString += " WHERE"
                containsConditions = true
            }
            queryString += " favorite = ?"
            args.add(fav)
        }

        // Add the main type filter if required
        mainType?.let { givenMainType ->
            if (givenMainType != "All") {
                if (containsConditions) {
                    queryString += " AND"
                } else {
                    queryString += " WHERE"
                    containsConditions = true
                }
                queryString += " poke_main_type = ?"
                args.add(givenMainType)
            }
        }

        // Add the secondary type filter if required
        secondaryType?.let { givenSecondaryType ->
            if (secondaryType != "All") {
                if (containsConditions) {
                    queryString += " AND"
                } else {
                    queryString += " WHERE"
                    containsConditions = true
                }
                queryString += " poke_secondary_type = ?"
                args.add(givenSecondaryType)
            }
        }

        // End of the query
        queryString += ";"

        Timber.d(queryString)

        // Perform the query
        val query = SimpleSQLiteQuery(queryString, args.toList().toTypedArray())

        return pokemonDao.getFilteredPokemons(query).toPokemonList()
    }
}