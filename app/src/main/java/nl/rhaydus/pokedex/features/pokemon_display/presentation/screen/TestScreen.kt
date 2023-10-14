package nl.rhaydus.pokedex.features.pokemon_display.presentation.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import nl.rhaydus.pokedex.core.presentation.navigation.TestNavGraph

@TestNavGraph(start = true)
@Destination
@Composable
fun TestScreen(navigator: DestinationsNavigator) {
    Text(text = "Test", modifier = Modifier.fillMaxSize())

}