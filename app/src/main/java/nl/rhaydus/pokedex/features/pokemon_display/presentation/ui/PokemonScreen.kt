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
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.launch
import nl.rhaydus.pokedex.R
import nl.rhaydus.pokedex.features.pokemon_display.presentation.ui.destinations.DetailedPokemonScreenDestination
import nl.rhaydus.pokedex.features.pokemon_display.presentation.viewmodel.PokemonScreenViewModel
import nl.rhaydus.pokedex.features.pokemon_display.presentation.widgets.BuildPokemonCard
import nl.rhaydus.pokedex.features.pokemon_display.presentation.widgets.ShowProgressDialog
import timber.log.Timber

@OptIn(ExperimentalMaterialApi::class)
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
    val favoritesFilterEnabled = remember { mutableStateOf(false) }

    val bottomSheetState =
        rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)

    val coScope = rememberCoroutineScope()

    val focusManager = LocalFocusManager.current

    val currentPokemonList = viewModel.currentPokemonList.observeAsState()
    val isLoading = viewModel.loadingState.observeAsState()

    if (isLoading.value == true) {
        ShowProgressDialog()
    }

    suspend fun applyFilter() {
        val filterOnFav = if (favoritesFilterEnabled.value) true else null
        viewModel.applyFilter(
            givenQuery = currentSearchFilter.value,
            favorites = filterOnFav
        )
    }

    Scaffold(
        backgroundColor = colorResource(id = R.color.color_background),
        topBar = {
            TopAppBar(
                backgroundColor = colorResource(id = R.color.color_top_bar),
                title = {
                    Text(stringResource(id = R.string.app_name), color = Color.White)
                }
            )
        }
    ) {
        // This column is required for the Scaffold padding
        Column(modifier = Modifier.padding(it)) {
            ModalBottomSheetLayout(
                sheetContent = {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Checkbox(
                                checked = (favoritesFilterEnabled.value),
                                onCheckedChange = { newValue ->
                                    favoritesFilterEnabled.value = newValue
                                    coScope.launch {
                                        applyFilter()
                                    }
                                }
                            )
                            Text(text = "Favorites only")
                        }
                    }
                },
                sheetState = bottomSheetState,
                scrimColor = colorResource(R.color.color_background).copy(alpha = 0.33f)
            ) {
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
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        TextField(
                            shape = RoundedCornerShape(dimensionResource(id = R.dimen.text_field_corners)),
                            value = currentSearchFilter.value,
                            label = { Text("Name or number") },
                            leadingIcon = { Icon(Icons.Default.Search, "search") },
                            singleLine = true,
                            keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
                            onValueChange = { newValue ->
                                Timber.d("Value has been changed to $newValue")
                                currentSearchFilter.value = newValue
                                coScope.launch {
                                    applyFilter()
                                }
                            },
                            colors = TextFieldDefaults.textFieldColors(
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                disabledIndicatorColor = Color.Transparent,
                            ),
                            modifier = Modifier.weight(weight = 0.9f)
                        )
                        IconButton(onClick = {
                            coScope.launch {
                                bottomSheetState.show()
                                Timber.d("Filter has been clicked!")
                            }
                        }, modifier = Modifier.weight(0.1f)) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_baseline_filter_alt_24),
                                contentDescription = "Filter icon",
                                tint = Color.White
                            )
                        }
                    }

                    currentPokemonList.value?.let { pokeList ->
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(2),
                            verticalArrangement = Arrangement.spacedBy(
                                dimensionResource(id = R.dimen.grid_card_spacing)
                            ),
                            horizontalArrangement = Arrangement.spacedBy(
                                dimensionResource(id = R.dimen.grid_card_spacing)
                            )
                        ) {
                            items(pokeList) { poke ->
                                BuildPokemonCard(givenPokemon = poke) {
                                    navigator.navigate(DetailedPokemonScreenDestination(poke))
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}