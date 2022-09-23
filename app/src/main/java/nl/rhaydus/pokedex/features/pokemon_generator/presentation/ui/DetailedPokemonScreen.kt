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
            backgroundColor = PokedexHelper.determineColor(poke),
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
            backgroundColor = PokedexHelper.determineColor(poke),
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
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(poke.name, style = MaterialTheme.typography.h4.copy(color = Color.White))
        }
    }
}