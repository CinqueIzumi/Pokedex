package nl.rhaydus.pokedex.features.pokemon_display.domain.repositories

import nl.rhaydus.pokedex.core.domain.model.Pokemon

interface PokemonRepository {
    suspend fun getSpecificPokemon(id: Int): Pokemon
    suspend fun getAllPokemon(): List<Pokemon>
    suspend fun getPokemonWithFilter(
        nameOrId: String?,
        isFavorite: Boolean?,
        mainType: String?
    ): List<Pokemon>

    suspend fun favoritePokemon(pokemon: Pokemon)
}