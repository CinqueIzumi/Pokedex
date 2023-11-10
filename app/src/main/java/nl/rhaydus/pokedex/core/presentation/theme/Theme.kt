package nl.rhaydus.pokedex.core.presentation.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.dp

// region Local providers
val LocalPokedexColors = staticCompositionLocalOf {
    PokedexColors(
        primary = Purple40,
        secondary = PurpleGrey40,
        tertiary = Pink40
    )
}

val LocalPokedexTypography = staticCompositionLocalOf {
    PokedexTypography(
        titleLarge = titleLarge,
        titleMedium = titleMedium,
        subTitleLarge = subtitleLarge,
        subTitleMedium = subtitleMedium,
        typeText = typeText,
        bodyText = bodyText,
        categoryText = categoryText,
        categoryBodyText = categoryBodyText,
        searchText = searchText
    )
}

val LocalPokedexShapes = staticCompositionLocalOf {
    PokedexShapes(
        button = RoundedCornerShape(8.dp),
        card = RoundedCornerShape(16.dp),
        cornerSmall = RoundedCornerShape(8.dp)
    )
}

val LocalPokedexPadding = staticCompositionLocalOf {
    PokedexPadding(
        xSmall = 4.dp,
        small = 8.dp,
        medium = 16.dp,
        large = 24.dp
    )
}

val LocalPokedexDimensions = staticCompositionLocalOf {
    PokedexDimensions(
        spacingXSmall = 4.dp,
        spacingSmall = 8.dp,
        spacingMedium = 16.dp,
        spacingLarge = 24.dp,
        spacingXLarge = 32.dp,
    )
}

val LocalPokedexSizes = staticCompositionLocalOf {
    PokedexSizes(
        dialog = 100.dp
    )
}
// endregion

// region Themes
object PokedexTheme {
    val typography: PokedexTypography
        @Composable
        get() = LocalPokedexTypography.current
    val shapes: PokedexShapes
        @Composable
        get() = LocalPokedexShapes.current
    val padding: PokedexPadding
        @Composable
        get() = LocalPokedexPadding.current
    val dimensions: PokedexDimensions
        @Composable
        get() = LocalPokedexDimensions.current
    val sizes: PokedexSizes
        @Composable
        get() = LocalPokedexSizes.current
    val colors: PokedexColors
        @Composable
        get() = LocalPokedexColors.current
}

@Composable
fun LightTheme(content: @Composable () -> Unit) {
    CompositionLocalProvider {
        MaterialTheme(content = content)
    }
}

@Composable
fun DarkTheme(content: @Composable () -> Unit) {
    val newColors = PokedexColors(
        primary = Pink80,
        secondary = Pink80,
        tertiary = Pink80
    )
    CompositionLocalProvider(
        LocalPokedexColors provides newColors
    ) {
        MaterialTheme(content = content)
    }
}
// endregion

@Composable
fun PokedexTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            if (darkTheme) {
                DarkTheme(content = content)
            } else {
                LightTheme(content = content)
            }
        }

        darkTheme -> DarkTheme(content = content)
        else -> LightTheme(content = content)
    }
}