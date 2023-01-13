package nl.rhaydus.pokedex.features.pokemon_display.data.data_sources

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import nl.rhaydus.pokedex.R
import nl.rhaydus.pokedex.core.EmptyPokemonBody
import nl.rhaydus.pokedex.features.pokemon_display.data.mapper.toPokemon
import nl.rhaydus.pokedex.features.pokemon_display.data.network.PokemonApiService
import nl.rhaydus.pokedex.features.pokemon_display.domain.model.Pokemon
import javax.inject.Inject

class RemotePokemonDataSourceImpl @Inject constructor(
    private val pokemonApiService: PokemonApiService,
    @ApplicationContext private val context: Context
) : RemotePokemonDataSource {

    private suspend fun getSpecificPokemonFromApi(pokemonId: Int): Pokemon {
        val response = pokemonApiService.getSpecificPokemon(id = pokemonId)
        return response.body()?.toPokemon() ?: throw EmptyPokemonBody()
    }

    override suspend fun getAllPokemon(): List<Pokemon> {
        val pokeList = mutableListOf<Pokemon>()

        coroutineScope {
            repeat(context.resources.getInteger(R.integer.highest_pokemon_id)) {
                launch {
                    pokeList.add(getSpecificPokemonFromApi(1 + it))
                }
            }
        }

        return pokeList.toList()
    }
}