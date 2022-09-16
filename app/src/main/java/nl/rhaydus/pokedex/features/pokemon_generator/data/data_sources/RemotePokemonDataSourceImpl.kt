package nl.rhaydus.pokedex.features.pokemon_generator.data.data_sources

import android.util.Log
import nl.rhaydus.pokedex.features.core.DEBUG_TAG
import nl.rhaydus.pokedex.features.core.EmptyPokemonBody
import nl.rhaydus.pokedex.features.core.HIGHEST_POKEMON_ID
import nl.rhaydus.pokedex.features.pokemon_generator.data.mapper.toPokemon
import nl.rhaydus.pokedex.features.pokemon_generator.data.network.PokemonApiService
import nl.rhaydus.pokedex.features.pokemon_generator.domain.model.Pokemon
import javax.inject.Inject

class RemotePokemonDataSourceImpl @Inject constructor(
    private val pokemonApiService: PokemonApiService
) : RemotePokemonDataSource {

    override suspend fun getRandomPokemonFromApi(): Pokemon {
        val randomId = (0..905).shuffled().last()
        return getSpecificPokemonFromApi(randomId)
    }

    override suspend fun getSpecificPokemonFromApi(pokemonId: Int): Pokemon {
        val response = pokemonApiService.getSpecificPokemon(pokemonId)
        return response.body()?.toPokemon() ?: throw EmptyPokemonBody()
    }

    override suspend fun getAllPokemon(): List<Pokemon> {
        val pokeList = mutableListOf<Pokemon>()

        for (i in 1..HIGHEST_POKEMON_ID) {
            val currentPoke = getSpecificPokemonFromApi(i)
            Log.d(DEBUG_TAG, "Current poke: $currentPoke")

            pokeList.add(getSpecificPokemonFromApi(i))
        }

        return pokeList.toList()
    }
}