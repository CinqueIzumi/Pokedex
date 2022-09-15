package nl.rhaydus.pokedex.features.pokemon_generator.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import kotlinx.coroutines.*
import nl.rhaydus.pokedex.R
import nl.rhaydus.pokedex.features.pokemon_generator.domain.model.Pokemon
import nl.rhaydus.pokedex.features.pokemon_generator.presentation.viewmodel.PokemonFragmentViewModel

@RootNavGraph(start = true)
@Composable
@Destination
fun PokemonScreen(
    viewModel: PokemonFragmentViewModel = hiltViewModel()
) {
    val currentPokemon = viewModel.currentPokemon.observeAsState()
    val isLoading = viewModel.loadingState.observeAsState()

    if (isLoading.value == true) {
        ShowProgressDialog()
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        TopAppBar(
            backgroundColor = MaterialTheme.colors.primarySurface,
        ) {
            Text(text = "Pokémon Randomizer")
        }
        Column(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxSize()
        ) {
            currentPokemon.value?.let { pokemon ->
                BuildPokemonCard(givenPokemon = pokemon)
            }
            Button(
                onClick = {
                    runBlocking {
                        withContext(Dispatchers.IO) {
                            viewModel.loadRandomPokemon()
                        }
                    }
                }
            ) {
                Text("Randomize!", style = MaterialTheme.typography.button)
            }
        }
    }
}

@Composable
fun ShowProgressDialog() {
    Dialog(
        onDismissRequest = {},
        DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false)
    ) {
        Box(
            modifier = Modifier
                .size(100.dp)
                .background(Color.White, shape = RoundedCornerShape(8.dp)),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }
}

@Composable
fun BuildPokemonCard(givenPokemon: Pokemon) {
    Card(
        backgroundColor = Color.Red
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            AsyncImage(
                model = ImageRequest
                    .Builder(LocalContext.current)
                    .data(givenPokemon.imageUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = "Pokemon image",
                modifier = Modifier.size(100.dp),
                placeholder = painterResource(R.drawable.ic_egg_sprite)
            )
            Text("Name: ${givenPokemon.name}", style = MaterialTheme.typography.h5)
            Text("ID: ${givenPokemon.id}", style = MaterialTheme.typography.subtitle1)
        }
    }
}