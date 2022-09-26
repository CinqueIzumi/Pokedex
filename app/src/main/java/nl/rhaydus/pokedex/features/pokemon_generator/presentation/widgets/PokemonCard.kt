package nl.rhaydus.pokedex.features.pokemon_generator.presentation.widgets

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import coil.request.ImageRequest
import nl.rhaydus.pokedex.R
import nl.rhaydus.pokedex.core.CARD_CORNERS_SMALL
import nl.rhaydus.pokedex.core.CARD_PADDING
import nl.rhaydus.pokedex.core.CARD_SPACING_GRID
import nl.rhaydus.pokedex.core.PokedexHelper
import nl.rhaydus.pokedex.features.pokemon_generator.domain.model.Pokemon

@Preview(showBackground = true)
@Composable
fun PokemonCardPreview() {
    val pokemon = Pokemon(
        name = "Snom",
        id = 872,
        imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/872.png",
        types = listOf("Ice"),
        height = 20,
        weight = 20,
        hpStat = 10,
        atkStat = 10,
        defStat = 10,
        spAtkStat = 10,
        spDefStat = 10,
        spdStat = 10
    )
    val listOfPokemon = listOf(pokemon, pokemon, pokemon, pokemon)

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        verticalArrangement = Arrangement.spacedBy(CARD_SPACING_GRID),
        horizontalArrangement = Arrangement.spacedBy(CARD_SPACING_GRID)
    ) {
        items(listOfPokemon) {
            BuildPokemonCard(givenPokemon = it) {
            }
        }
    }
}


@Composable
fun BuildPokemonCard(
    givenPokemon: Pokemon,
    onClick: () -> Unit,
) {
    Card(
        backgroundColor = PokedexHelper.determineColor(givenPokemon),
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onClick.invoke()
            },
        shape = RoundedCornerShape(CARD_CORNERS_SMALL)
    ) {
        Column(
            modifier = Modifier.padding(CARD_PADDING),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                model = ImageRequest
                    .Builder(LocalContext.current)
                    .data(givenPokemon.imageUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = "Pokemon image",
                modifier = Modifier
                    .fillMaxWidth(),
                placeholder = painterResource(R.drawable.ic_egg_sprite)
            )
            Text(
                givenPokemon.name, style = MaterialTheme.typography.h5.copy(Color.White)
            )
            Text(
                "#${givenPokemon.id}",
                style = MaterialTheme.typography.subtitle1.copy(Color.White)
            )
        }
    }
}