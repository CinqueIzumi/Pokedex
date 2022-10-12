package nl.rhaydus.pokedex.features.pokemon_display.domain.repositories

import nl.rhaydus.pokedex.features.pokemon_display.domain.model.Pokemon

interface PokemonRepository {
    suspend fun getAllPokemon(): Result<List<Pokemon>>
    suspend fun favoritePokemon(pokemon: Pokemon): Result<Unit>
    suspend fun unFavoritePokemon(pokemon: Pokemon): Result<Unit>
    suspend fun getPokemonWithFilter(
        nameOrId: String?,
        isFavorite: Boolean?,
        mainType: String?,
        secondaryType: String?
    ): Result<List<Pokemon>>
}