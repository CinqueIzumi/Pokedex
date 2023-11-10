package nl.rhaydus.pokedex.features.pokemon_display.data.datasource

import nl.rhaydus.pokedex.features.pokemon_display.domain.model.Pokemon

interface RemotePokemonDataSource {
    /**
     * @throws [PokemonDisplayExceptions.EmptyResponseBody] Response was successful but body was empty
     * @throws [PokemonDisplayExceptions.ApiError] Response was unsuccessful
     */
    suspend fun getSpecificPokemon(id: Int): Pokemon
}