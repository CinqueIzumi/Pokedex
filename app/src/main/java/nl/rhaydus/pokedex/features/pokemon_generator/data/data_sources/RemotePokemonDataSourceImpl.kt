package nl.rhaydus.pokedex.features.pokemon_generator.data.data_sources

import nl.rhaydus.pokedex.features.core.EmptyUser
import nl.rhaydus.pokedex.features.pokemon_generator.data.mapper.toPokemon
import nl.rhaydus.pokedex.features.pokemon_generator.data.network.PokemonApiService
import nl.rhaydus.pokedex.features.pokemon_generator.domain.model.Pokemon
import javax.inject.Inject

class RemotePokemonDataSourceImpl @Inject constructor(
    private val pokemonApiService: PokemonApiService
): RemotePokemonDataSource {

    override suspend fun getRandomPokemonFromApi(): Pokemon {
        val randomId = (0..900).shuffled().last()

        val response = pokemonApiService.getRandomPokemon(randomId)

        return response.body()?.toPokemon() ?: throw EmptyUser()
    }
}