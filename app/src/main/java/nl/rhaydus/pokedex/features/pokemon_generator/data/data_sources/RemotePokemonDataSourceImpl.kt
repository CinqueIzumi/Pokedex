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

    private suspend fun getSpecificPokemonFromApi(pokemonId: Int): Pokemon {
        val response = pokemonApiService.getSpecificPokemon(pokemonId)
        return response.body()?.toPokemon() ?: throw EmptyPokemonBody()
    }

    override suspend fun getAllPokemon(): List<Pokemon> {
        val pokeList = mutableListOf<Pokemon>()

        for (i in 1..HIGHEST_POKEMON_ID) {
            if (i % 100 == 0) {
                Timber.d("Loaded $i pokemons!")
            }
            pokeList.add(getSpecificPokemonFromApi(i))
        }

        return pokeList.toList()
    }
}