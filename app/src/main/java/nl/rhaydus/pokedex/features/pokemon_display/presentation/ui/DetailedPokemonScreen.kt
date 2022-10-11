package nl.rhaydus.pokedex.features.pokemon_display.presentation.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.ramcosta.composedestinations.annotation.Destination
import kotlinx.coroutines.launch
import nl.rhaydus.pokedex.R
import nl.rhaydus.pokedex.core.*
import nl.rhaydus.pokedex.features.pokemon_display.domain.model.Pokemon
import nl.rhaydus.pokedex.features.pokemon_display.presentation.viewmodel.DetailedPokemonScreenViewModel
import nl.rhaydus.pokedex.features.pokemon_display.presentation.widgets.ShowProgressDialog

@Composable
@Destination
fun DetailedPokemonScreen(
    viewModel: DetailedPokemonScreenViewModel = hiltViewModel(),
    poke: Pokemon,
    navigator: NavController
) {
    val coroutineScope = rememberCoroutineScope()

    // Check whether the pokemon was added to the favorites on initial launch
    LaunchedEffect(key1 = true) {
        viewModel.checkIfFavorite(poke)
    }

    val isLoading = viewModel.loadingState.observeAsState()
    val isFavorite = viewModel.isFavoriteState.observeAsState()

    if (isLoading.value == true) {
        ShowProgressDialog()
    }

    Column(modifier = Modifier.fillMaxSize()) {
        TopAppBar(
            backgroundColor = colorResource(
                id = PokedexHelper.determineTypeColor(poke.types[0])
            ),
            title = {
                Text(stringResource(id = R.string.app_name), color = Color.White)
            },
            elevation = 0.dp,
            navigationIcon = {
                if (navigator.previousBackStackEntry != null) {
                    IconButton(onClick = {
                        navigator.navigateUp()
                    }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                }
            },
            actions = {
                IconButton(
                    onClick = {
                        coroutineScope.launch {
                            if (isFavorite.value == true) {
                                viewModel.unFavoritePokemon(poke)
                            } else {
                                viewModel.favoritePokemon(poke)
                            }
                        }
                    })
                {
                    Icon(
                        imageVector = if (isFavorite.value == true) {
                            Icons.Filled.Favorite
                        } else {
                            Icons.Default.FavoriteBorder
                        },
                        contentDescription = "Favorite",
                    )
                }
            }
        )

        Card(
            modifier = Modifier.fillMaxWidth(),
            backgroundColor = colorResource(
                id = PokedexHelper.determineTypeColor(poke.types[0])
            ),
            elevation = 0.dp,
            shape = RoundedCornerShape(
                0.dp,
                0.dp,
                dimensionResource(id = R.dimen.card_corner_big),
                dimensionResource(id = R.dimen.card_corner_big)
            )
        ) {
            AsyncImage(
                model = ImageRequest
                    .Builder(LocalContext.current)
                    .data(poke.imageUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = "Pokemon image",
                modifier = Modifier
                    .width(dimensionResource(id = R.dimen.image_max_size))
                    .height(dimensionResource(id = R.dimen.image_max_size)),
                placeholder = painterResource(R.drawable.ic_egg_sprite)
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(id = R.dimen.default_column_padding)),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(poke.name, style = MaterialTheme.typography.h4)

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                horizontalArrangement = Arrangement.SpaceAround,
                modifier = Modifier.fillMaxWidth()
            ) {
                for (type in poke.types) {
                    BuildTypePill(type)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                horizontalArrangement = Arrangement.SpaceAround,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column {
                    Text(
                        "${poke.weight} KG",
                        style = MaterialTheme.typography.h6
                    )
                    Text(
                        "Weight", style = MaterialTheme.typography.subtitle1.copy(
                            color = Color.Gray
                        )
                    )
                }

                Column {
                    Text(
                        "${poke.height} M",
                        style = MaterialTheme.typography.h6
                    )
                    Text(
                        "Height", style = MaterialTheme.typography.subtitle1.copy(
                            color = Color.Gray
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text("Base Stats", style = MaterialTheme.typography.h6)

            BuildStatRow(statName = "HP", value = poke.hpStat)
            BuildStatRow(statName = "ATK", value = poke.atkStat)
            BuildStatRow(statName = "DEF", value = poke.defStat)
            BuildStatRow(statName = "SP.ATK", value = poke.spAtkStat)
            BuildStatRow(statName = "SP.DEF", value = poke.spDefStat)
            BuildStatRow(statName = "SPD", value = poke.spdStat)
        }
    }
}

@Composable
fun BuildTypePill(type: String) {
    Surface(
        shape = RoundedCornerShape(20.dp),
        color = colorResource(id = PokedexHelper.determineTypeColor(type)),
        modifier = Modifier.width((LocalConfiguration.current.screenWidthDp * 0.4).dp)
    ) {
        Row(horizontalArrangement = Arrangement.Center) {
            Text(
                type,
                modifier = Modifier.padding(4.dp),
                style = MaterialTheme.typography.subtitle1
            )
        }
    }
}

@Composable
fun BuildStatRow(statName: String, value: Int) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(0.2f),
            text = statName,
            style = MaterialTheme.typography.subtitle1.copy(Color.LightGray)
        )
        LinearProgressIndicator(
            progress = (value / 255f),
            color = colorResource(id = PokedexHelper.determineStatColor(statName)),
            backgroundColor = Color.White,
            modifier = Modifier.fillMaxWidth()
        )
    }
}