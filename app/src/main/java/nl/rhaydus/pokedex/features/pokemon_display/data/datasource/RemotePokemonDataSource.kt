package nl.rhaydus.pokedex.features.pokemon_display.data.datasource

import nl.rhaydus.pokedex.core.domain.model.Pokemon
import nl.rhaydus.pokedex.features.pokemon_display.domain.exception.PokemonDisplayException

interface RemotePokemonDataSource {
    /**
     * @exception [PokemonDisplayException.EmptyResponseBody] Response was successful but body was empty
     * @exception [PokemonDisplayException.ApiError] Response was unsuccessful
     */
    suspend fun getSpecificPokemon(id: Int): Pokemon
}