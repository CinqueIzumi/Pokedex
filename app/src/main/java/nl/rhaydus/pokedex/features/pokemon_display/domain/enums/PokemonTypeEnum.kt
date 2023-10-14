package nl.rhaydus.pokedex.features.pokemon_display.domain.enums

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color
import nl.rhaydus.pokedex.R

enum class PokemonTypeEnum(@ColorRes val colorId: Int, @DrawableRes val typeImage: Int) {
    NORMAL(R.color.color_type_normal, R.drawable.normal),
    FIRE(R.color.color_type_fire, R.drawable.fire),
    FIGHTING(R.color.color_type_fighting, R.drawable.fighting),
    WATER(R.color.color_type_water, R.drawable.water),
    FLYING(R.color.color_type_flying, R.drawable.flying),
    GRASS(R.color.color_type_grass, R.drawable.grass),
    POISON(R.color.color_type_poison, R.drawable.poison),
    ELECTRIC(R.color.color_type_electric, R.drawable.electric),
    GROUND(R.color.color_type_ground, R.drawable.ground),
    PSYCHIC(R.color.color_type_psychic, R.drawable.psychic),
    ROCK(R.color.color_type_rock, R.drawable.rock),
    ICE(R.color.color_type_ice, R.drawable.ice),
    BUG(R.color.color_type_bug, R.drawable.bug),
    DRAGON(R.color.color_type_dragon, R.drawable.dragon),
    GHOST(R.color.color_type_ghost, R.drawable.ghost),
    DARK(R.color.color_type_dark, R.drawable.dark),
    STEEL(R.color.color_type_steel, R.drawable.steel),
    FAIRY(R.color.color_type_fairy, R.drawable.fairy);

    // STEEL -> Steel
    fun getName(): String = this.name.lowercase().replaceFirstChar { it.uppercase() }

    fun getTextColor(): Color {
        return if (this == DRAGON || this == GHOST || this == FIGHTING || this == DARK) {
            Color.White
        } else {
            Color.Black
        }
    }
}