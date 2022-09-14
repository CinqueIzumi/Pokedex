package nl.rhaydus.pokedex.features.pokemon_generator.presentation.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import nl.rhaydus.pokedex.features.pokemon_generator.presentation.viewmodel.PokemonFragmentViewModel

@RootNavGraph(start = true)
@Composable
@Destination
fun PokemonScreen(
    viewModel: PokemonFragmentViewModel = hiltViewModel()
) {
    val state = viewModel.currentPokemon.observeAsState()

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
            Card(
                modifier = Modifier.fillMaxWidth(),
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text("Name: ${state.value?.name}", style = MaterialTheme.typography.h5)
                    Text("ID: ${state.value?.id}", style = MaterialTheme.typography.subtitle1)
                    Image(
                        painter = rememberAsyncImagePainter(state.value?.imageUrl),
                        contentDescription = null,
                        modifier = Modifier.size(100.dp)
                    )
                }
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