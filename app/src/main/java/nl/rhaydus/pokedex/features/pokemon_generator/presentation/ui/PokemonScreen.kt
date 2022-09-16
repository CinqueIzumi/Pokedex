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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
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
import nl.rhaydus.pokedex.features.core.*
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

@Preview(showBackground = true)
@Composable
fun PokemonCardPreview() {
    val pokemon = Pokemon(
        "Snom",
        872,
        "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/872.png",
        "Ice"
    )

    Column(modifier = Modifier.fillMaxSize()) {
        BuildPokemonCard(givenPokemon = pokemon)
    }
}

val cardPadding: Dp = 8.dp

@Composable
fun BuildPokemonCard(givenPokemon: Pokemon) {
    Card(
        backgroundColor = determineColor(givenPokemon),
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(cardPadding)
        ) {
            Row {
                Column {
                    Text(
                        givenPokemon.name,
                        style = MaterialTheme.typography.h5,
                        color = Color.White
                    )
                    Text(
                        givenPokemon.mainType,
                        style = MaterialTheme.typography.subtitle1,
                        color = Color.White
                    )
                    Text(
                        "#${givenPokemon.id}",
                        style = MaterialTheme.typography.subtitle1,
                        color = Color.White
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                Column {
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
                }
            }
        }
    }
}

fun determineColor(givenPokemon: Pokemon): Color {
    return when (givenPokemon.mainType) {
        "Normal" -> COLOR_TYPE_NORMAL
        "Fighting" -> COLOR_TYPE_FIGHTING
        "Poison" -> COLOR_TYPE_POISON
        "Ground" -> COLOR_TYPE_GROUND
        "Rock" -> COLOR_TYPE_ROCK
        "Bug" -> COLOR_TYPE_BUG
        "Ghost" -> COLOR_TYPE_GHOST
        "Steel" -> COLOR_TYPE_STEEL
        "Fire" -> COLOR_TYPE_FIRE
        "Water" -> COLOR_TYPE_WATER
        "Grass" -> COLOR_TYPE_GRASS
        "Electric" -> COLOR_TYPE_ELECTRIC
        "Psychic" -> COLOR_TYPE_PSYCHIC
        "Ice" -> COLOR_TYPE_ICE
        "Dragon" -> COLOR_TYPE_DRAGON
        "Dark" -> COLOR_TYPE_DARK
        "Fairy" -> COLOR_TYPE_FAIRY
        "Unknown" -> COLOR_TYPE_UNKNOWN
        "Flying" -> COLOR_TYPE_FLYING
        else -> Color.Black
    }
}