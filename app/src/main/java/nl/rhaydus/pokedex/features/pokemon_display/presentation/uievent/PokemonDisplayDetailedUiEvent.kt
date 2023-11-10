package nl.rhaydus.pokedex.features.pokemon_display.presentation.uievent

import nl.rhaydus.pokedex.features.pokemon_display.domain.model.Pokemon

sealed class PokemonDisplayDetailedUiEvent {
    data class InitializePokemon(val pokemon: Pokemon) : PokemonDisplayDetailedUiEvent()
}