package nl.rhaydus.pokedex.core

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import nl.rhaydus.pokedex.R
import javax.inject.Inject

class PokedexHelper @Inject constructor(
    @ApplicationContext private val context: Context
) {
    fun determineTypeColor(type: String): Int {
        return when (type) {
            "Normal" -> R.color.color_type_normal
            "Fighting" -> R.color.color_type_fighting
            "Poison" -> R.color.color_type_poison
            "Ground" -> R.color.color_type_ground
            "Rock" -> R.color.color_type_rock
            "Bug" -> R.color.color_type_bug
            "Ghost" -> R.color.color_type_ghost
            "Steel" -> R.color.color_type_steel
            "Fire" -> R.color.color_type_fire
            "Water" -> R.color.color_type_water
            "Grass" -> R.color.color_type_grass
            "Electric" -> R.color.color_type_electric
            "Psychic" -> R.color.color_type_psychic
            "Ice" -> R.color.color_type_ice
            "Dragon" -> R.color.color_type_dragon
            "Dark" -> R.color.color_type_dark
            "Fairy" -> R.color.color_type_fairy
            "Unknown" -> R.color.color_type_unknown
            "Flying" -> R.color.color_type_flying
            else -> R.color.black
        }
    }

    fun determineStatColor(stat: String): Int {
        return when (stat) {
            "HP" -> R.color.color_stat_hp
            "ATK" -> R.color.color_stat_atk
            "DEF" -> R.color.color_stat_def
            "SP.ATK" -> R.color.color_stat_sp_atk
            "SP.DEF" -> R.color.color_stat_sp_def
            "SPD" -> R.color.color_stat_spd
            else -> R.color.black
        }
    }
}