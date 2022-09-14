package nl.rhaydus.pokedex.features.pokemon_generator.presentation.ui

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import nl.rhaydus.pokedex.features.core.DEBUG_TAG
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
//                    Text("Name: Snom", style = MaterialTheme.typography.h5)
                    Text("Name: ${state.value?.name}", style = MaterialTheme.typography.h5)
                    Text("ID: 872", style = MaterialTheme.typography.subtitle1)
                }
            }
            Button(
                onClick = {
                    Log.d(DEBUG_TAG, "cLICKED!")
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