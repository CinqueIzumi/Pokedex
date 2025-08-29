package nl.rhaydus.pokedex.feature.type.domain.model

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color
import nl.rhaydus.pokedex.R
import nl.rhaydus.pokedex.core.presentation.theme.TYPE_BUG_COLOR
import nl.rhaydus.pokedex.core.presentation.theme.TYPE_DARK_COLOR
import nl.rhaydus.pokedex.core.presentation.theme.TYPE_DRAGON_COLOR
import nl.rhaydus.pokedex.core.presentation.theme.TYPE_ELECTRIC_COLOR
import nl.rhaydus.pokedex.core.presentation.theme.TYPE_FAIRY_COLOR
import nl.rhaydus.pokedex.core.presentation.theme.TYPE_FIGHTING_COLOR
import nl.rhaydus.pokedex.core.presentation.theme.TYPE_FIRE_COLOR
import nl.rhaydus.pokedex.core.presentation.theme.TYPE_FLYING_COLOR
import nl.rhaydus.pokedex.core.presentation.theme.TYPE_GHOST_COLOR
import nl.rhaydus.pokedex.core.presentation.theme.TYPE_GRASS_COLOR
import nl.rhaydus.pokedex.core.presentation.theme.TYPE_GROUND_COLOR
import nl.rhaydus.pokedex.core.presentation.theme.TYPE_ICE_COLOR
import nl.rhaydus.pokedex.core.presentation.theme.TYPE_NORMAL_COLOR
import nl.rhaydus.pokedex.core.presentation.theme.TYPE_POISON_COLOR
import nl.rhaydus.pokedex.core.presentation.theme.TYPE_PSYCHIC_COLOR
import nl.rhaydus.pokedex.core.presentation.theme.TYPE_ROCK_COLOR
import nl.rhaydus.pokedex.core.presentation.theme.TYPE_STEEL_COLOR
import nl.rhaydus.pokedex.core.presentation.theme.TYPE_WATER_COLOR

enum class PokemonType(
    @DrawableRes val iconResource: Int,
    val color: Color,
) {
    NORMAL(
        iconResource = R.drawable.ic_type_normal,
        color = TYPE_NORMAL_COLOR
    ),
    FIRE(
        iconResource = R.drawable.ic_type_fire,
        color = TYPE_FIRE_COLOR
    ),
    FIGHTING(
        iconResource = R.drawable.ic_type_fighting,
        color = TYPE_FIGHTING_COLOR
    ),
    WATER(
        iconResource = R.drawable.ic_type_water,
        color = TYPE_WATER_COLOR
    ),
    FLYING(
        iconResource = R.drawable.ic_type_flying,
        color = TYPE_FLYING_COLOR
    ),
    GRASS(
        iconResource = R.drawable.ic_type_grass,
        color = TYPE_GRASS_COLOR
    ),
    POISON(
        iconResource = R.drawable.ic_type_poison,
        color = TYPE_POISON_COLOR
    ),
    ELECTRIC(
        iconResource = R.drawable.ic_type_electric,
        color = TYPE_ELECTRIC_COLOR
    ),
    GROUND(
        iconResource = R.drawable.ic_type_ground,
        color = TYPE_GROUND_COLOR
    ),
    PSYCHIC(
        iconResource = R.drawable.ic_type_psychic,
        color = TYPE_PSYCHIC_COLOR
    ),
    ROCK(
        iconResource = R.drawable.ic_type_rock,
        color = TYPE_ROCK_COLOR
    ),
    ICE(
        iconResource = R.drawable.ic_type_ice,
        color = TYPE_ICE_COLOR
    ),
    BUG(
        iconResource = R.drawable.ic_type_bug,
        color = TYPE_BUG_COLOR
    ),
    DRAGON(
        iconResource = R.drawable.ic_type_dragon,
        color = TYPE_DRAGON_COLOR
    ),
    GHOST(
        iconResource = R.drawable.ic_type_ghost,
        color = TYPE_GHOST_COLOR
    ),
    DARK(
        iconResource = R.drawable.ic_type_dark,
        color = TYPE_DARK_COLOR
    ),
    STEEL(
        iconResource = R.drawable.ic_type_steel,
        color = TYPE_STEEL_COLOR
    ),
    FAIRY(
        iconResource = R.drawable.ic_type_fairy,
        color = TYPE_FAIRY_COLOR
    ),
}