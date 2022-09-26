package nl.rhaydus.pokedex.features.pokemon_display.presentation.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.runBlocking
import nl.rhaydus.pokedex.R
import nl.rhaydus.pokedex.features.pokemon_display.domain.model.Pokemon
import nl.rhaydus.pokedex.features.pokemon_display.presentation.ui.destinations.DetailedPokemonScreenDestination
import nl.rhaydus.pokedex.features.pokemon_display.presentation.viewmodel.PokemonScreenViewModel
import nl.rhaydus.pokedex.features.pokemon_display.presentation.widgets.BuildPokemonCard
import nl.rhaydus.pokedex.features.pokemon_display.presentation.widgets.ShowProgressDialog
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
            backgroundColor = colorResource(id = R.color.color_top_bar),
            title = {
                Text(stringResource(id = R.string.app_name), color = Color.White)
            }
        )
        Column(
            modifier = Modifier
                .padding(dimensionResource(id = R.dimen.default_column_padding))
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                "Search for a Pokémon by name or using its National Pokédex number.",
                style = MaterialTheme.typography.subtitle1
            )
            TextField(
                shape = RoundedCornerShape(dimensionResource(id = R.dimen.text_field_corners)),
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
        verticalArrangement = Arrangement.spacedBy(
            dimensionResource(id = R.dimen.grid_card_spacing)
        ),
        horizontalArrangement = Arrangement.spacedBy(
            dimensionResource(id = R.dimen.grid_card_spacing)
        )
    ) {
        items(pokemon) { poke ->
            BuildPokemonCard(givenPokemon = poke) {
                navigator.navigate(DetailedPokemonScreenDestination(poke))
            }
        }
    }
}