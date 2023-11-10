package nl.rhaydus.pokedex.core.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.animations.rememberAnimatedNavHostEngine
import dagger.hilt.android.AndroidEntryPoint
import nl.rhaydus.pokedex.NavGraphs
import nl.rhaydus.pokedex.core.presentation.component.SystemUiController
import nl.rhaydus.pokedex.core.presentation.theme.PokedexTheme
import nl.rhaydus.pokedex.core.presentation.viewmodel.MainViewModel

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val mainViewModel: MainViewModel by viewModels()

    @OptIn(ExperimentalAnimationApi::class, ExperimentalMaterialNavigationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainViewModel.initialize()

        installSplashScreen().apply {
            this.setKeepOnScreenCondition {
                mainViewModel.showSplashScreen.value
            }
        }

        setContent {
            val splashState by mainViewModel.showSplashScreen.collectAsStateWithLifecycle()

            if (!splashState) {
                PokedexTheme {
                    SystemUiController {
                        val navController = rememberNavController()
                        Box(modifier = Modifier.fillMaxSize()) {
                            DestinationsNavHost(
                                navController = navController,
                                navGraph = NavGraphs.root,
                                engine = rememberAnimatedNavHostEngine()
                            )
                        }
                    }
                }
            }
        }
    }
}