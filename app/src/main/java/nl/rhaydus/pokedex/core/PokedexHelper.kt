package nl.rhaydus.pokedex.core

import androidx.compose.ui.graphics.Color
import nl.rhaydus.pokedex.features.pokemon_generator.domain.model.Pokemon

object PokedexHelper {
    fun determineColor(givenPokemon: Pokemon): Color {
        return when (givenPokemon.types[0]) {
            "Normal" -> COLOR_TYPE_NORMAL
            "Fighting" -> COLOR_TYPE_FIGHTING
            "Poison" -> COLOR_TYPE_POISON
            "Ground" -> COLOR_TYPE_GROUND
            "Rock" -> COLOR_TYPE_ROCK
            "Bug" -> COLOR_TYPE_BUG
            "Ghost" -> COLOR_TYPE_GHOST
            "Steel" -> COLOR_TYPE_STEEL
            "Fire" -> COLOR_TYPE_FIRE
            "Water" -> COLOR_TYPE_WATER
            "Grass" -> COLOR_TYPE_GRASS
            "Electric" -> COLOR_TYPE_ELECTRIC
            "Psychic" -> COLOR_TYPE_PSYCHIC
            "Ice" -> COLOR_TYPE_ICE
            "Dragon" -> COLOR_TYPE_DRAGON
            "Dark" -> COLOR_TYPE_DARK
            "Fairy" -> COLOR_TYPE_FAIRY
            "Unknown" -> COLOR_TYPE_UNKNOWN
            "Flying" -> COLOR_TYPE_FLYING
            else -> Color.Black
        }
    }
}