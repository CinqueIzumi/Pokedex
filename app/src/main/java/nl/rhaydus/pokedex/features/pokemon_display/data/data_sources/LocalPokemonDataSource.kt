package nl.rhaydus.pokedex.features.pokemon_display.data.data_sources

import nl.rhaydus.pokedex.features.pokemon_display.domain.model.Pokemon

interface LocalPokemonDataSource {
    suspend fun getAllPokemon(): List<Pokemon>
    suspend fun addPokemons(pokes: List<Pokemon>)
    suspend fun isLocalDataComplete(): Boolean
    suspend fun favoritePokemon(pokemon: Pokemon)
    suspend fun unFavoritePokemon(pokemon: Pokemon)
    suspend fun getPokemonWithFilter(
        nameOrId: String?,
        isFavorite: Boolean?,
        mainType: String?,
        secondaryType: String?
    ): List<Pokemon>
}