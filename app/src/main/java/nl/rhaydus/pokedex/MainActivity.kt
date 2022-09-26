package nl.rhaydus.pokedex

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.ramcosta.composedestinations.DestinationsNavHost
import dagger.hilt.android.AndroidEntryPoint
import nl.rhaydus.pokedex.features.pokemon_display.presentation.ui.NavGraphs

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val customTypography = MaterialTheme.typography.copy(
                h4 = MaterialTheme.typography.h4.copy(color = Color.White),
                h5 = MaterialTheme.typography.h5.copy(color = Color.White),
                h6 = MaterialTheme.typography.h6.copy(color = Color.White),
                subtitle1 = MaterialTheme.typography.subtitle1.copy(color = Color.White),
            )

            MaterialTheme(darkColors(), typography = customTypography) {
                val systemUiController = rememberSystemUiController()
                systemUiController.setStatusBarColor(color = colorResource(R.color.color_top_bar))

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = colorResource(R.color.color_background)
                ) {
                    DestinationsNavHost(navGraph = NavGraphs.root)
                }
            }
        }
    }
}