package nl.rhaydus.pokedex.core.presentation.theme

import androidx.compose.ui.graphics.Shape
import javax.annotation.concurrent.Immutable

@Immutable
data class PokedexShapes(
    val button: Shape,
    val card: Shape,
    val cornerSmall: Shape
)