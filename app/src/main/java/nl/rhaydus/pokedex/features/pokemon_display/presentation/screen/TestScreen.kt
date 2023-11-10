package nl.rhaydus.pokedex.features.pokemon_display.presentation.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import nl.rhaydus.pokedex.core.presentation.navigation.AccountNavGraph
import nl.rhaydus.pokedex.core.presentation.navigation.FavoritesNavGraph
import nl.rhaydus.pokedex.core.presentation.navigation.RegionNavGraph

@RegionNavGraph(start = true)
@Destination
@Composable
fun RegionScreen(navigator: DestinationsNavigator) {
    Text(text = "Regions", modifier = Modifier.fillMaxSize())
}

@FavoritesNavGraph(start = true)
@Destination
@Composable
fun FavoritesScreen(navigator: DestinationsNavigator) {
    Text(text = "Favorites", modifier = Modifier.fillMaxSize())
}

@AccountNavGraph(start = true)
@Destination
@Composable
fun AccountScreen(navigator: DestinationsNavigator) {
    Text(text = "Account", modifier = Modifier.fillMaxSize())
}