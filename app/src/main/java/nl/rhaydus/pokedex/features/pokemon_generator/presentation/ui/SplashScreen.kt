package nl.rhaydus.pokedex.features.pokemon_generator.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import nl.rhaydus.pokedex.features.pokemon_generator.presentation.ui.destinations.PokemonScreenDestination
import nl.rhaydus.pokedex.features.pokemon_generator.presentation.viewmodel.SplashScreenViewModel

@Composable
@Destination
@RootNavGraph(start = true)
fun SplashScreen(
    navigator: DestinationsNavigator,
    viewModel: SplashScreenViewModel = hiltViewModel()
) {
    val toNavigate = remember { mutableStateOf(false) }

    LaunchedEffect(key1 = true) {
        viewModel.initializePokemon()
        toNavigate.value = true
    }

    if (toNavigate.value) {
        // Clear the backstack to prevent going back to splash screen
        navigator.popBackStack()
        navigator.navigate(PokemonScreenDestination())
    }

    Dialog(
        onDismissRequest = {},
        DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .background(Color.White, shape = RoundedCornerShape(8.dp))
                .padding(20.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                Text("Loading...")
                CircularProgressIndicator()
            }
        }
    }
}