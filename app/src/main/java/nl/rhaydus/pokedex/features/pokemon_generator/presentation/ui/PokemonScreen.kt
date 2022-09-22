package nl.rhaydus.pokedex.features.pokemon_generator.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.ramcosta.composedestinations.annotation.Destination
import kotlinx.coroutines.runBlocking
import nl.rhaydus.pokedex.R
import nl.rhaydus.pokedex.core.*
import nl.rhaydus.pokedex.features.pokemon_generator.domain.model.Pokemon
import nl.rhaydus.pokedex.features.pokemon_generator.presentation.viewmodel.PokemonScreenViewModel
import timber.log.Timber

@Composable
@Destination
fun PokemonScreen(
    viewModel: PokemonScreenViewModel = hiltViewModel()
) {
    LaunchedEffect(true) {
        viewModel.getPokemonFromRoom()
    }

    val currentSearchFilter = remember { mutableStateOf("") }
    val focusManager = LocalFocusManager.current

    val currentPokemonList = viewModel.currentPokemonList.observeAsState()
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
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            TextField(
                value = currentSearchFilter.value,
                singleLine = true,
                onValueChange = { newValue ->
                    Timber.d("Value has been changed to $newValue")
                    currentSearchFilter.value = newValue
                    runBlocking {
                        viewModel.applyFilter(newValue)
                    }
                },
                keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
                label = { Text("Search by name, type or id") }
            )
            currentPokemonList.value?.let { pokeList ->
                PokemonCardList(pokemon = pokeList)
            }
        }
    }
}

@Composable
fun PokemonCardList(pokemon: List<Pokemon>) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(pokemon) { poke ->
            BuildPokemonCard(givenPokemon = poke)
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

@Composable
fun BuildPokemonCard(givenPokemon: Pokemon) {
    Card(
        backgroundColor = PokedexHelper.determineColor(givenPokemon),
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(CARD_CORNERS)
    ) {
        Column(
            modifier = Modifier.padding(CARD_PADDING)
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

