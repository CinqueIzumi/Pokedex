package nl.rhaydus.pokedex.features.pokemon_generator.data.data_sources

import nl.rhaydus.pokedex.features.core.EmptyUser
import nl.rhaydus.pokedex.features.pokemon_generator.data.mapper.toPokemon
import nl.rhaydus.pokedex.features.pokemon_generator.data.network.PokemonApiService
import nl.rhaydus.pokedex.features.pokemon_generator.domain.model.Pokemon

class RemotePokemonDataSourceImpl : RemotePokemonDataSource {
    private val service: PokemonApiService = PokemonApiService.getPokemonApiService()

    override suspend fun getRandomPokemonFromApi(): Pokemon {
        val randomId = (0..900).shuffled().last()

        val response = service.getRandomPokemon(randomId)

        return response.body()?.toPokemon() ?: throw EmptyUser()
    }
}