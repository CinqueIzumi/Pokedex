package nl.rhaydus.pokedex.core.presentation.component

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun SystemUiController(
    systemColor: Color = MaterialTheme.colors.background,
    navigationColor: Color = MaterialTheme.colors.background,
    useDarkIcons: Boolean = !isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val systemUiController = rememberSystemUiController()

    DisposableEffect(systemUiController, useDarkIcons, systemColor) {

        systemUiController.apply {
            setStatusBarColor(
                color = systemColor,
                darkIcons = useDarkIcons
            )

            setNavigationBarColor(
                color = navigationColor,
                darkIcons = useDarkIcons
            )
        }

        onDispose {}
    }

    content()
}