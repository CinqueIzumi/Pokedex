package nl.rhaydus.pokedex.features.pokemon_display.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.sqlite.db.SimpleSQLiteQuery
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import nl.rhaydus.pokedex.features.pokemon_display.data.dao.PokemonDao
import nl.rhaydus.pokedex.features.pokemon_display.data.mapper.toPokemon
import nl.rhaydus.pokedex.features.pokemon_display.data.mapper.toPokemonList
import nl.rhaydus.pokedex.features.pokemon_display.data.model.PokemonEntity
import nl.rhaydus.pokedex.features.pokemon_display.domain.model.Pokemon
import timber.log.Timber
import javax.inject.Inject
import kotlin.Exception

@HiltViewModel
class PokemonScreenViewModel @Inject constructor(
    private val pokeDao: PokemonDao,
) : ViewModel() {

    private val _currentPokemonList = MutableLiveData<List<Pokemon>?>()
    val currentPokemonList: LiveData<List<Pokemon>?> = _currentPokemonList

    private val _loadingState = MutableLiveData(true)
    val loadingState: LiveData<Boolean> = _loadingState

    suspend fun applyFilter(
        givenQuery: String? = null,
        favorites: Boolean? = null,
        mainType: String? = null,
        secondaryType: String? = null
    ) {
        withContext(Dispatchers.IO) {
            _loadingState.postValue(true)

            try {
                var queryString = ""
                val args: MutableList<Any> = mutableListOf()
                var containsConditions = false

                // Start the assembly of the query string
                queryString += "SELECT * FROM pokemonentity"

                // Add the search filter if required
                givenQuery?.let { givenQuery ->
                    // Check whether the given query is numerical (id) or a pokemon name
                    when (givenQuery.toIntOrNull()) {
                        null -> {
                            queryString += " WHERE poke_name LIKE ?"
                            args.add("%$givenQuery%")
                        }
                        else -> {
                            queryString += " WHERE id = ?"
                            args.add(givenQuery)
                        }
                    }

                    containsConditions = true
                }

                // Add the favorites filter if required
                favorites?.let { fav ->
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
                mainType?.let { mainType ->
                    if (mainType != "All") {
                        if (containsConditions) {
                            queryString += " AND"
                        } else {
                            queryString += " WHERE"
                            containsConditions = true
                        }
                        queryString += " poke_main_type = ?"
                        args.add(mainType)
                    }
                }

                // Add the secondary type filter if required
                secondaryType?.let { secondaryType ->
                    if (secondaryType != "All") {
                        if (containsConditions) {
                            queryString += " AND"
                        } else {
                            queryString += " WHERE"
                            containsConditions = true
                        }
                        queryString += " poke_secondary_type = ?"
                        args.add(secondaryType)
                    }
                }

                // End of the query
                queryString += ";"

                Timber.d(queryString)

                // Perform the query
                val query = SimpleSQLiteQuery(queryString, args.toList().toTypedArray())
                val pokeList: List<Pokemon> = pokeDao.getFilteredPokemons(query).toPokemonList()

                _currentPokemonList.postValue(pokeList)
            } catch (e: Exception) {
                Timber.e(e)
            }

            _loadingState.postValue(false)
        }
    }

    private fun initializeList(givenEntityList: List<PokemonEntity>) {
        val pokemonList = mutableListOf<Pokemon>()

        for (pokeEntity in givenEntityList) {
            pokemonList.add(pokeEntity.toPokemon())
        }

        _currentPokemonList.postValue(pokemonList)
    }

    suspend fun getPokemonFromRoom() {
        withContext(Dispatchers.IO) {
            _loadingState.postValue(true)

            try {
                val pokeEntityList: List<PokemonEntity> = pokeDao.getAll()
                initializeList(pokeEntityList)
            } catch (e: Exception) {
                Timber.e(e)
            }

            _loadingState.postValue(false)
        }
    }
}