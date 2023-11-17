package nl.rhaydus.pokedex.features.pokemon_display.data.datasource

import nl.rhaydus.pokedex.core.domain.model.Pokemon

interface LocalPokemonDataSource {
    suspend fun getAllPokemon(): List<Pokemon>
    suspend fun addPokemon(pokemon: Pokemon)
    suspend fun getPokemonWithFilter(
        nameOrId: String?,
        isFavorite: Boolean?,
        mainType: String?
    ): List<Pokemon>
}