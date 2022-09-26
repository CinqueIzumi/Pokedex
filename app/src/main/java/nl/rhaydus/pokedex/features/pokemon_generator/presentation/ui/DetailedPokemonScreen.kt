package nl.rhaydus.pokedex.features.pokemon_generator.presentation.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.ramcosta.composedestinations.annotation.Destination
import nl.rhaydus.pokedex.R
import nl.rhaydus.pokedex.core.*
import nl.rhaydus.pokedex.features.pokemon_generator.domain.model.Pokemon

@Composable
@Destination
fun DetailedPokemonScreen(
    poke: Pokemon,
    navigator: NavController
) {
    Column(modifier = Modifier.fillMaxSize()) {
        TopAppBar(
            backgroundColor = PokedexHelper.determineTypeColor(poke.types[0]),
            title = {
                Text(APP_TITLE, color = Color.White)
            },
            elevation = 0.dp,
            navigationIcon = {
                if (navigator.previousBackStackEntry != null) {
                    IconButton(onClick = { navigator.navigateUp() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                }
            }
        )

        Card(
            modifier = Modifier.fillMaxWidth(),
            backgroundColor = PokedexHelper.determineTypeColor(poke.types[0]),
            elevation = 0.dp,
            shape = RoundedCornerShape(0.dp, 0.dp, CARD_CORNERS_BIG, CARD_CORNERS_BIG)
        ) {
            AsyncImage(
                model = ImageRequest
                    .Builder(LocalContext.current)
                    .data(poke.imageUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = "Pokemon image",
                modifier = Modifier
                    .width(200.dp)
                    .height(200.dp),
                placeholder = painterResource(R.drawable.ic_egg_sprite)
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(poke.name, style = MaterialTheme.typography.h4.copy(color = Color.White))
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
                        "${poke.weight / 10} KG",
                        style = MaterialTheme.typography.h6.copy(Color.White)
                    )
                    Text("Weight", style = MaterialTheme.typography.subtitle1.copy(Color.Gray))
                }

                Column {
                    Text(
                        "${poke.height / 10} M",
                        style = MaterialTheme.typography.h6.copy(Color.White)
                    )
                    Text("Height", style = MaterialTheme.typography.subtitle1.copy(Color.Gray))
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text("Base Stats", style = MaterialTheme.typography.h6.copy(Color.White))

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
        color = PokedexHelper.determineTypeColor(type),
        modifier = Modifier.width((LocalConfiguration.current.screenWidthDp * 0.4).dp)
    ) {
        Row(horizontalArrangement = Arrangement.Center) {
            Text(
                type,
                modifier = Modifier.padding(4.dp),
                style = MaterialTheme.typography.subtitle1.copy(color = Color.White)
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
            color = PokedexHelper.determineStatColor(statName),
            backgroundColor = Color.White,
            modifier = Modifier.fillMaxWidth()
        )
    }
}