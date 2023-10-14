package nl.rhaydus.pokedex.core.presentation.theme

import androidx.compose.ui.text.TextStyle
import javax.annotation.concurrent.Immutable

@Immutable
data class PokedexTypography(
    val titleLarge: TextStyle,
    val titleMedium: TextStyle,
    val subTitleLarge: TextStyle,
    val subTitleMedium: TextStyle,
    val typeText: TextStyle,
    val bodyText: TextStyle,
    val categoryText: TextStyle,
    val categoryBodyText: TextStyle,
    val searchText: TextStyle
)