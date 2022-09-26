package nl.rhaydus.pokedex.features.pokemon_display.data.data_sources

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import nl.rhaydus.pokedex.R
import nl.rhaydus.pokedex.core.EmptyPokemonBody
import nl.rhaydus.pokedex.features.pokemon_display.data.mapper.toPokemon
import nl.rhaydus.pokedex.features.pokemon_display.data.network.PokemonApiService
import nl.rhaydus.pokedex.features.pokemon_display.domain.model.Pokemon
import timber.log.Timber
import javax.inject.Inject

class RemotePokemonDataSourceImpl @Inject constructor(
    private val pokemonApiService: PokemonApiService,
    @ApplicationContext private val context: Context
) : RemotePokemonDataSource {

    private suspend fun getSpecificPokemonFromApi(pokemonId: Int): Pokemon {
        val response = pokemonApiService.getSpecificPokemon(pokemonId)
        return response.body()?.toPokemon() ?: throw EmptyPokemonBody()
    }

    override suspend fun getAllPokemon(): List<Pokemon> {
        val pokeList = mutableListOf<Pokemon>()

        for (i in 1..context.resources.getInteger(R.integer.highest_pokemon_id)) {
            if (i % 100 == 0) {
                Timber.d("Loaded $i pokemons!")
            }
            pokeList.add(getSpecificPokemonFromApi(i))
        }

        return pokeList.toList()
    }
}