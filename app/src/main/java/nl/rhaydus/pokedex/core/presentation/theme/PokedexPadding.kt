package nl.rhaydus.pokedex.core.presentation.theme

import androidx.compose.ui.unit.Dp
import javax.annotation.concurrent.Immutable

@Immutable
data class PokedexPadding(
    val xSmall: Dp,
    val small: Dp,
    val medium: Dp,
    val large: Dp
)