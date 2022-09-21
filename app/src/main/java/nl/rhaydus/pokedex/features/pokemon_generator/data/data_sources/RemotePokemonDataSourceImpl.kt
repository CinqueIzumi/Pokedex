package nl.rhaydus.pokedex.features.pokemon_generator.data.data_sources

import nl.rhaydus.pokedex.core.EmptyPokemonBody
import nl.rhaydus.pokedex.core.HIGHEST_POKEMON_ID
import nl.rhaydus.pokedex.features.pokemon_generator.data.mapper.toPokemon
import nl.rhaydus.pokedex.features.pokemon_generator.data.network.PokemonApiService
import nl.rhaydus.pokedex.features.pokemon_generator.domain.model.Pokemon
import timber.log.Timber
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
        return getPokemonUntilId(HIGHEST_POKEMON_ID)
    }

    override suspend fun getPokemonUntilId(id: Int): List<Pokemon> {
        val pokeList = mutableListOf<Pokemon>()

        for (i in 1..id) {
            val currentPoke = getSpecificPokemonFromApi(i)
            Timber.d("Current poke: $currentPoke")

            pokeList.add(currentPoke)
        }

        return pokeList.toList()
    }
}