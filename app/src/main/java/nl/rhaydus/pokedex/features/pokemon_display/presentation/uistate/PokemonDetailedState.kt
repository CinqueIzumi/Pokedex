package nl.rhaydus.pokedex.features.pokemon_display.presentation.uistate

import nl.rhaydus.pokedex.features.pokemon_display.domain.model.Pokemon

data class PokemonDetailedState(val pokemon: Pokemon? = null, val isLoading: Boolean = false)