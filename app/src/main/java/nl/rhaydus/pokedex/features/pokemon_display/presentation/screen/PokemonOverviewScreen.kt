package nl.rhaydus.pokedex.features.pokemon_display.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import nl.rhaydus.pokedex.R
import nl.rhaydus.pokedex.core.data.constant.PREVIEW_POKEMON
import nl.rhaydus.pokedex.core.domain.util.BottomNavBarManager
import nl.rhaydus.pokedex.core.presentation.navigation.OverviewNavGraph
import nl.rhaydus.pokedex.core.presentation.theme.DefaultPreviews
import nl.rhaydus.pokedex.core.presentation.theme.PokedexTheme
import nl.rhaydus.pokedex.destinations.PokemonDetailedScreenDestination
import nl.rhaydus.pokedex.features.pokemon_display.domain.enums.PokemonSortEnum
import nl.rhaydus.pokedex.features.pokemon_display.domain.enums.PokemonTypeEnum
import nl.rhaydus.pokedex.features.pokemon_display.domain.model.Pokemon
import nl.rhaydus.pokedex.features.pokemon_display.presentation.component.PokemonCardListItem
import nl.rhaydus.pokedex.features.pokemon_display.presentation.uievent.PokemonDisplayOverviewUiEvent
import nl.rhaydus.pokedex.features.pokemon_display.presentation.viewmodel.PokemonOverviewViewModel

@OverviewNavGraph(start = true)
@Destination
@Composable
fun PokemonOverViewScreen(
    navigator: DestinationsNavigator,
    vm: PokemonOverviewViewModel = hiltViewModel(),
) {
    val state by vm.pokemonDisplayScreenState.collectAsStateWithLifecycle()

    // You'll always want the screen to be redrawn, as opening a 'new' pokemon 'unlocks' the detailed entries when going back
    LaunchedEffect(true) {
        BottomNavBarManager.showBottomNavBar(show = true)
        vm.handleEvent(PokemonDisplayOverviewUiEvent.GetAllPokemon)
    }

    fun onCardClicked(pokemon: Pokemon) {
        navigator.navigate(PokemonDetailedScreenDestination(pokemon))
    }

    fun onEvent(event: PokemonDisplayOverviewUiEvent) {
        vm.handleEvent(event)
    }

    PokemonOverViewScreen(
        pokemonList = state.pokemonList,
        isLoading = state.isLoading,
        onClicked = ::onCardClicked,
        onEvent = ::onEvent,
        typeFilter = state.filteredType,
        sortType = state.sortType
    )
}

@Composable
fun PokemonOverViewScreen(
    pokemonList: List<Pokemon>,
    isLoading: Boolean,
    typeFilter: PokemonTypeEnum? = null,
    sortType: PokemonSortEnum? = null,
    onClicked: (Pokemon) -> Unit = {},
    onEvent: (PokemonDisplayOverviewUiEvent) -> Unit = {}
) {
    val givenText = remember { mutableStateOf("") }
    val typeExpanded = remember { mutableStateOf(false) }
    val sortExpanded = remember { mutableStateOf(false) }

    Scaffold {
        Column(
            modifier = Modifier
                .padding(it)
                .padding(PokedexTheme.padding.medium),
            verticalArrangement = Arrangement.spacedBy(PokedexTheme.dimensions.spacingMedium)
        ) {
            if (typeExpanded.value) {
                BuildTypeSelectorSheet(
                    onDismissRequest = {
                        typeExpanded.value = false
                        onEvent(PokemonDisplayOverviewUiEvent.UpdateFilterWithType(null))
                    },
                    onTypeSelected = { type: PokemonTypeEnum ->
                        typeExpanded.value = false
                        onEvent(PokemonDisplayOverviewUiEvent.UpdateFilterWithType(type))
                    }
                )
            }

            if (sortExpanded.value) {
                BuildSortSelectorSheet(
                    onDismissRequest = {
                        sortExpanded.value = false
                        onEvent(PokemonDisplayOverviewUiEvent.UpdateListWithSort(PokemonSortEnum.NUMBER_ASCENDING))
                    },
                    onSortSelected = { sort: PokemonSortEnum ->
                        sortExpanded.value = false
                        onEvent(PokemonDisplayOverviewUiEvent.UpdateListWithSort(sort))
                    }
                )
            }

            BuildSearchField(
                value = givenText.value,
                onValueChanged = { newValue: String ->
                    givenText.value = newValue

                    onEvent(PokemonDisplayOverviewUiEvent.GetPokemonWithNameOrId(nameOrId = givenText.value))
                }
            )

            SortRow(
                typeFilter = typeFilter,
                sortType = sortType,
                onTypeClicked = { typeExpanded.value = true },
                onSortClicked = { sortExpanded.value = true }
            )

            Box {
                if (isLoading) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        CircularProgressIndicator()
                    }
                }

                LazyColumn(verticalArrangement = Arrangement.spacedBy(PokedexTheme.dimensions.spacingSmall)) {
                    items(pokemonList) { pokemon ->
                        PokemonCardListItem(pokemon = pokemon, onClicked)
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BuildSortSelectorSheet(
    onDismissRequest: () -> Unit,
    onSortSelected: (type: PokemonSortEnum) -> Unit
) {
    ModalBottomSheet(onDismissRequest = onDismissRequest) {
        Column(modifier = Modifier.padding(PokedexTheme.padding.medium)) {
            Text(
                text = stringResource(id = R.string.bottom_sheet_order_name_number_title),
                style = PokedexTheme.typography.titleMedium,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(PokedexTheme.dimensions.spacingXLarge))

            LazyColumn(verticalArrangement = Arrangement.spacedBy(PokedexTheme.dimensions.spacingSmall)) {
                items(PokemonSortEnum.values()) { sortingType: PokemonSortEnum ->
                    Box(
                        modifier = Modifier
                            .background(
                                color = Color.Black,
                                shape = RoundedCornerShape(percent = 50)
                            )
                            .padding(12.dp)
                            .fillMaxWidth()
                            .clickable {
                                onSortSelected(sortingType)
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = sortingType.description.getString(), color = Color.White)
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BuildTypeSelectorSheet(
    onDismissRequest: () -> Unit,
    onTypeSelected: (type: PokemonTypeEnum) -> Unit
) {
    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
    ) {
        Column(
            modifier = Modifier.padding(PokedexTheme.padding.medium)
        ) {
            Text(
                text = stringResource(id = R.string.bottom_sheet_order_type_title),
                style = PokedexTheme.typography.titleMedium,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(PokedexTheme.dimensions.spacingXLarge))

            LazyColumn(verticalArrangement = Arrangement.spacedBy(PokedexTheme.dimensions.spacingSmall)) {
                items(PokemonTypeEnum.values()) { type: PokemonTypeEnum ->
                    Box(
                        modifier = Modifier
                            .background(
                                color = colorResource(id = type.colorId),
                                shape = RoundedCornerShape(percent = 50)
                            )
                            .padding(12.dp)
                            .fillMaxWidth()
                            .clickable {
                                onTypeSelected(type)
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = type.getName(), color = type.getTextColor())
                    }
                }
            }

        }
    }
}

@Composable
private fun SortRow(
    typeFilter: PokemonTypeEnum?,
    sortType: PokemonSortEnum?,
    onTypeClicked: () -> Unit,
    onSortClicked: () -> Unit
) {
    Row {
        val boxColor: Color = typeFilter?.colorId?.let { colorId: Int ->
            colorResource(id = colorId)
        } ?: Color(0xFF333333)

        Box(
            modifier = Modifier
                .weight(1f)
                .background(
                    color = boxColor,
                    shape = RoundedCornerShape(percent = 50)
                )
                .padding(PokedexTheme.padding.medium)
                .clickable {
                    onTypeClicked()
                },
            contentAlignment = Alignment.Center
        ) {
            Row {
                val text = typeFilter?.getName()
                    ?: stringResource(id = R.string.pokemon_overview_type_filter_placeholder)
                val textColor = typeFilter?.getTextColor() ?: Color.White

                Text(text = text, color = textColor, modifier = Modifier.weight(1f))
                Icon(
                    Icons.Default.ExpandMore,
                    contentDescription = "Expand",
                    tint = Color.White
                )
            }
        }

        Spacer(modifier = Modifier.width(PokedexTheme.dimensions.spacingMedium))

        Box(
            modifier = Modifier
                .weight(1f)
                .background(
                    color = Color(0xFF333333),
                    shape = RoundedCornerShape(percent = 50)
                )
                .padding(PokedexTheme.padding.medium)
                .clickable { onSortClicked() },
            contentAlignment = Alignment.Center
        ) {
            Row {
                val text: String = sortType?.description?.getString()
                    ?: stringResource(id = R.string.pokemon_overview_name_number_filter_placeholder)
                Text(text = text, color = Color.White, modifier = Modifier.weight(1f))
                Icon(
                    Icons.Default.ExpandMore,
                    contentDescription = "Expand",
                    tint = Color.White
                )
            }
        }
    }
}

@Composable
fun BuildSearchField(value: String, onValueChanged: (String) -> Unit) {
    OutlinedTextField(
        shape = RoundedCornerShape(percent = 50),
        value = value,
        onValueChange = onValueChanged,
        modifier = Modifier.fillMaxWidth(),
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedBorderColor = Color(0xFFCCCCCC)
        ),
        leadingIcon = {
            Icon(
                Icons.Default.Search,
                contentDescription = "Search icon",
                tint = Color(0xFF666666)
            )
        },
        placeholder = {
            Text(
                text = stringResource(id = R.string.pokemon_overview_search_filter_placeholder),
                style = PokedexTheme.typography.searchText
            )
        },
        singleLine = true
    )
}

@DefaultPreviews
@Composable
fun PokemonOverViewScreenPreview() {
    PokedexTheme {
        val pokemonList = listOf(PREVIEW_POKEMON)
        PokemonOverViewScreen(pokemonList, false)
    }
}
