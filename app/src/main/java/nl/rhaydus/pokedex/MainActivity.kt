package nl.rhaydus.pokedex

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.ramcosta.composedestinations.DestinationsNavHost
import dagger.hilt.android.AndroidEntryPoint
import nl.rhaydus.pokedex.core.COLOR_BACKGROUND
import nl.rhaydus.pokedex.core.COLOR_TOP_BAR
import nl.rhaydus.pokedex.features.pokemon_generator.presentation.ui.NavGraphs

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme(darkColors()) {
                val systemUiController = rememberSystemUiController()
                SideEffect {
                    systemUiController.setStatusBarColor(color = COLOR_TOP_BAR)
                }

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = COLOR_BACKGROUND
                ) {
                    DestinationsNavHost(navGraph = NavGraphs.root)
                }
            }
        }
    }
}