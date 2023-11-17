package nl.rhaydus.pokedex.core.presentation.screen

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.animations.defaults.RootNavGraphDefaultAnimations
import com.ramcosta.composedestinations.animations.rememberAnimatedNavHostEngine
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import nl.rhaydus.pokedex.NavGraphs
import nl.rhaydus.pokedex.core.domain.util.HomeWidgetManager
import nl.rhaydus.pokedex.core.presentation.component.BottomNavBar
import nl.rhaydus.pokedex.core.presentation.component.SystemUiController

@OptIn(ExperimentalAnimationApi::class, ExperimentalMaterialNavigationApi::class)
@RootNavGraph(start = true)
@Destination
@Composable
fun HomeScreen() {
    val navController = rememberNavController()

    val showBottomNavBar = HomeWidgetManager.bottomNavBarVisible.collectAsState()
    val trayColor = HomeWidgetManager.notificationTrayColor.collectAsState()

    SystemUiController(systemColor = trayColor.value ?: MaterialTheme.colors.background) {
        Scaffold(
            bottomBar = {
                BottomNavBar(
                    navController = navController,
                    showNavBar = showBottomNavBar.value
                )
            },
        ) { innerPadding ->
            DestinationsNavHost(
                navGraph = NavGraphs.bottom,
                modifier = Modifier.padding(innerPadding),
                navController = navController,
                engine = rememberAnimatedNavHostEngine(
                    rootDefaultAnimations = RootNavGraphDefaultAnimations(
                        enterTransition = {
                            fadeIn(tween(200))
                        },
                        exitTransition = {
                            fadeOut(tween(200))
                        },
                    ),
                )
            )
        }
    }
}
