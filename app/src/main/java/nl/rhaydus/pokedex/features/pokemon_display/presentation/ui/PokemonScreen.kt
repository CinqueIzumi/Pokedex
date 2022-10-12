package nl.rhaydus.pokedex.features.pokemon_display.presentation.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.selection.selectable
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
import androidx.compose.ui.semantics.Role
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
    // When opening the screen for the first time, the pokemons from the local database should be initialized
    LaunchedEffect(true) {
        Timber.d("Started loading pokemon in overview!")
        viewModel.initializePokemon()
    }

    val currentSearchFilter = remember { mutableStateOf("") }
    val favoritesFilterEnabled = remember { mutableStateOf(false) }
    val selectedValueMain = remember { mutableStateOf("All") }
    val selectedValueSecondary = remember { mutableStateOf("All") }

    val coScope = rememberCoroutineScope()
    val bottomSheetState =
        rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)

    val focusManager = LocalFocusManager.current

    val currentPokemonList = viewModel.currentPokemonList.observeAsState()
    val isLoading = viewModel.loadingState.observeAsState()

    suspend fun applyFilter() {
        Timber.d("Started applying filter!")
        val filterOnFav = if (favoritesFilterEnabled.value) true else null

        viewModel.applyFilter(
            givenQuery = currentSearchFilter.value,
            favorites = filterOnFav,
            mainType = selectedValueMain.value,
            secondaryType = selectedValueSecondary.value
        )
    }

    val isSelectedItemMain: (String) -> Boolean =
        { givenString -> selectedValueMain.value == givenString }
    val onChangeStateMain: (String) -> Unit =
        { givenString ->
            selectedValueMain.value = givenString
            coScope.launch {
                applyFilter()
            }
        }

    val isSelectedItemSecondary: (String) -> Boolean =
        { givenString -> selectedValueSecondary.value == givenString }
    val onChangeStateSecondary: (String) -> Unit =
        { givenString ->
            selectedValueSecondary.value = givenString
            coScope.launch {
                applyFilter()
            }
        }

    val allTypes = listOf(
        "All",
        "Normal",
        "Fighting",
        "Poison",
        "Ground",
        "Rock",
        "Bug",
        "Ghost",
        "Steel",
        "Fire",
        "Water",
        "Grass",
        "Electric",
        "Psychic",
        "Ice",
        "Dragon",
        "Dark",
        "Fairy",
        "Unknown",
        "Flying",
    )

    if (isLoading.value == true) {
        ShowProgressDialog()
    }

    Scaffold(
        backgroundColor = colorResource(id = R.color.color_background),
        topBar = {
            TopAppBar(
                backgroundColor = colorResource(id = R.color.color_top_bar),
                title = {
                    Text(text = stringResource(id = R.string.app_name), color = Color.White)
                }
            )
        }
    ) {
        // This column is required for the Scaffold padding
        Column(modifier = Modifier.padding(it)) {
            ModalBottomSheetLayout(
                sheetContent = {
                    Column(modifier = Modifier.padding(20.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Checkbox(
                                checked = (favoritesFilterEnabled.value),
                                onCheckedChange = { newValue ->
                                    favoritesFilterEnabled.value = newValue
                                    Timber.d("Favorites enabled has been changed to: ${favoritesFilterEnabled.value}")
                                    coScope.launch {
                                        applyFilter()
                                    }
                                }
                            )
                            Text(text = "Favorites", style = MaterialTheme.typography.body1)
                        }
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column {
                                Text("Main type", style = MaterialTheme.typography.h6)
                                allTypes.forEach { item ->
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        modifier = Modifier.selectable(
                                            selected = isSelectedItemMain(item),
                                            onClick = { onChangeStateMain(item) },
                                            role = Role.RadioButton
                                        )
                                    ) {
                                        RadioButton(
                                            selected = isSelectedItemMain(item),
                                            onClick = null
                                        )
                                        Text(text = item, style = MaterialTheme.typography.body1)
                                    }
                                }
                            }
                            Column {
                                Text("Secondary type", style = MaterialTheme.typography.h6)
                                allTypes.forEach { item ->
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        modifier = Modifier.selectable(
                                            selected = isSelectedItemSecondary(item),
                                            onClick = { onChangeStateSecondary(item) },
                                            role = Role.RadioButton
                                        )
                                    ) {
                                        RadioButton(
                                            selected = isSelectedItemSecondary(item),
                                            onClick = null
                                        )
                                        Text(text = item, style = MaterialTheme.typography.body1)
                                    }
                                }
                            }
                        }
                    }
                },
                sheetState = bottomSheetState,
                scrimColor = colorResource(R.color.color_background).copy(alpha = 0.33f),
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
                                currentSearchFilter.value = newValue
                                Timber.d("Query has been changed to ${currentSearchFilter.value}")

                                coScope.launch {
                                    applyFilter()
                                }
                            },
                            colors = TextFieldDefaults.textFieldColors(
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                disabledIndicatorColor = Color.Transparent,
                                textColor = Color.White,
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
                                    Timber.d("Started navigating to: ${poke.name}")
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