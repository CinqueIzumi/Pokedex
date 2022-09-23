package nl.rhaydus.pokedex.features.pokemon_generator.presentation.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.runBlocking
import nl.rhaydus.pokedex.core.*
import nl.rhaydus.pokedex.features.pokemon_generator.domain.model.Pokemon
import nl.rhaydus.pokedex.features.pokemon_generator.presentation.ui.destinations.DetailedPokemonScreenDestination
import nl.rhaydus.pokedex.features.pokemon_generator.presentation.viewmodel.PokemonScreenViewModel
import nl.rhaydus.pokedex.features.pokemon_generator.presentation.widgets.BuildPokemonCard
import nl.rhaydus.pokedex.features.pokemon_generator.presentation.widgets.ShowProgressDialog
import timber.log.Timber

@Composable
@Destination
fun PokemonScreen(
    viewModel: PokemonScreenViewModel = hiltViewModel(),
    navigator: DestinationsNavigator
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
            backgroundColor = COLOR_TOP_BAR,
            title = {
                Text(APP_TITLE, color = Color.White)
            }
        )
        Column(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                "Search for a Pokémon by name or using its National Pokédex number.",
                style = MaterialTheme.typography.subtitle1.copy(color = Color.White)
            )
            TextField(
                shape = RoundedCornerShape(TEXT_FIELD_CORNERS),
                value = currentSearchFilter.value,
                label = { Text("Name, type or number") },
                leadingIcon = { Icon(Icons.Default.Search, "search") },
                singleLine = true,
                keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
                onValueChange = { newValue ->
                    Timber.d("Value has been changed to $newValue")
                    currentSearchFilter.value = newValue
                    runBlocking {
                        viewModel.applyFilter(newValue)
                    }
                },
                colors = TextFieldDefaults.textFieldColors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                ),
                modifier = Modifier.fillMaxWidth()
            )
            currentPokemonList.value?.let { pokeList ->
                PokemonCardList(pokemon = pokeList, navigator)
            }
        }
    }
}

@Composable
fun PokemonCardList(pokemon: List<Pokemon>, navigator: DestinationsNavigator) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        verticalArrangement = Arrangement.spacedBy(CARD_SPACING_GRID),
        horizontalArrangement = Arrangement.spacedBy(CARD_SPACING_GRID)
    ) {
        items(pokemon) { poke ->
            BuildPokemonCard(givenPokemon = poke) {
                navigator.navigate(DetailedPokemonScreenDestination(poke))
            }
        }
    }
}