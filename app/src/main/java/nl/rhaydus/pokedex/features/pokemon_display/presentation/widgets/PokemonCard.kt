package nl.rhaydus.pokedex.features.pokemon_display.presentation.widgets

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImage
import coil.request.ImageRequest
import nl.rhaydus.pokedex.R
import nl.rhaydus.pokedex.core.PokedexHelper
import nl.rhaydus.pokedex.features.pokemon_display.domain.model.Pokemon

@Composable
fun BuildPokemonCard(
    givenPokemon: Pokemon,
    onClick: () -> Unit,
) {
    Card(
        backgroundColor = colorResource(id = PokedexHelper.determineTypeColor(givenPokemon.types[0])),
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onClick.invoke()
            },
        shape = RoundedCornerShape(dimensionResource(id = R.dimen.card_corner_small))
    ) {
        Column(
            modifier = Modifier.padding(dimensionResource(id = R.dimen.card_padding)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                model = ImageRequest
                    .Builder(LocalContext.current)
                    .data(givenPokemon.imageUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = "Pokemon image",
                modifier = Modifier.fillMaxWidth(),
                placeholder = painterResource(R.drawable.ic_egg_sprite)
            )
            Text(text = givenPokemon.name, style = MaterialTheme.typography.h5)
            Text(text = "#${givenPokemon.id}", style = MaterialTheme.typography.subtitle1)
        }
    }
}