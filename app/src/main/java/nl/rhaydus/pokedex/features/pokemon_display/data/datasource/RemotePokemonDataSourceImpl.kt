package nl.rhaydus.pokedex.features.pokemon_display.data.datasource

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import nl.rhaydus.pokedex.core.domain.model.Pokemon
import nl.rhaydus.pokedex.features.pokemon_display.data.mapper.toPokemon
import nl.rhaydus.pokedex.features.pokemon_display.data.network.PokemonApiService
import nl.rhaydus.pokedex.features.pokemon_display.domain.exception.PokemonDisplayException
import javax.inject.Inject

class RemotePokemonDataSourceImpl @Inject constructor(
    private val pokemonApiService: PokemonApiService
) : RemotePokemonDataSource {
    override suspend fun getSpecificPokemon(id: Int): Pokemon {
        val pokemonResponse = withContext(Dispatchers.IO) {
            pokemonApiService.getSpecificPokemon(id)
        }
        val pokemonSpeciesResponse = withContext(Dispatchers.IO) {
            pokemonApiService.getSpecificPokemonSpecies(id)
        }

        if (!pokemonResponse.isSuccessful || !pokemonSpeciesResponse.isSuccessful) {
            throw PokemonDisplayException.ApiError
        }

        val pokemonResponseBody = pokemonResponse.body()
        val pokemonSpeciesBody = pokemonSpeciesResponse.body()
        if (pokemonResponseBody == null || pokemonSpeciesBody == null) {
            throw PokemonDisplayException.EmptyResponseBody
        }

        return pokemonResponseBody.toPokemon(pokemonSpeciesBody)
    }
}