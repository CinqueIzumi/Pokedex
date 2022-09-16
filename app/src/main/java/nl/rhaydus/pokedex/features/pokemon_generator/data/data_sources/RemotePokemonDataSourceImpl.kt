package nl.rhaydus.pokedex.features.pokemon_generator.data.data_sources

import nl.rhaydus.pokedex.features.core.EmptyPokemonBody
import nl.rhaydus.pokedex.features.pokemon_generator.data.mapper.toPokemon
import nl.rhaydus.pokedex.features.pokemon_generator.data.network.PokemonApiService
import nl.rhaydus.pokedex.features.pokemon_generator.domain.model.Pokemon
import javax.inject.Inject

class RemotePokemonDataSourceImpl @Inject constructor(
    private val pokemonApiService: PokemonApiService
) : RemotePokemonDataSource {

    override suspend fun getRandomPokemonFromApi(): Pokemon {
        val randomId = (0..900).shuffled().last()
        return getSpecificPokemonFromApi(randomId)
    }

    override suspend fun getSpecificPokemonFromApi(pokemonId: Int): Pokemon {
        val response = pokemonApiService.getSpecificPokemon(pokemonId)
        return response.body()?.toPokemon() ?: throw EmptyPokemonBody()
    }
}