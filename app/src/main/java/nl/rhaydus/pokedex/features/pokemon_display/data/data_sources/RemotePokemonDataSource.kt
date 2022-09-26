package nl.rhaydus.pokedex.features.pokemon_display.data.data_sources

import nl.rhaydus.pokedex.features.pokemon_display.domain.model.Pokemon

interface RemotePokemonDataSource {
    suspend fun getAllPokemon(): List<Pokemon>
}