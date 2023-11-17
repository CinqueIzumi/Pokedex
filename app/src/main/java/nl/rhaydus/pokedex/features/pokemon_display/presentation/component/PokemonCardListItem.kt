package nl.rhaydus.pokedex.features.pokemon_display.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import coil.compose.AsyncImage
import nl.rhaydus.pokedex.R
import nl.rhaydus.pokedex.core.data.constant.PREVIEW_POKEMON
import nl.rhaydus.pokedex.core.domain.model.Pokemon
import nl.rhaydus.pokedex.core.presentation.theme.DefaultPreviews
import nl.rhaydus.pokedex.core.presentation.theme.PokedexTheme
import nl.rhaydus.pokedex.features.pokemon_display.domain.enums.PokemonTypeEnum

@Composable
fun PokemonCardListItem(pokemon: Pokemon, onClicked: (Pokemon) -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = colorResource(
                    id = pokemon.mainType?.colorId ?: R.color.color_type_unknown
                ).copy(alpha = 0.1f),
                shape = PokedexTheme.shapes.card
            )
            .clickable {
                onClicked(pokemon)
            }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min)
        ) {
            PokemonCardListItemInfo(
                pokemon = pokemon,
                modifier = Modifier
                    .padding(
                        top = PokedexTheme.padding.small,
                        start = PokedexTheme.padding.medium,
                        bottom = PokedexTheme.padding.small
                    )
                    .weight(1f)
            )

            PokemonCardListItemImage(pokemon = pokemon)
        }
    }
}

@Composable
fun PokemonCardListItemImage(pokemon: Pokemon) {
    val imageSize: Dp = 80.dp
    pokemon.mainType?.let { mainType: PokemonTypeEnum ->
        Box(
            modifier = Modifier
                .background(
                    color = colorResource(id = mainType.colorId),
                    shape = PokedexTheme.shapes.card
                )
                .padding(PokedexTheme.padding.small)
                .fillMaxHeight(1f)
                .width(IntrinsicSize.Min)
        ) {
            Image(
                painter = painterResource(mainType.typeImage),
                contentDescription = "desc",
                modifier = Modifier
                    .zIndex(1f)
                    .align(Alignment.Center)
                    .height(imageSize)
                    .width(imageSize),
                colorFilter = ColorFilter.tint(color = Color.White.copy(alpha = 0.7f))
            )
            AsyncImage(
                model = pokemon.artworkUrl,
                contentDescription = "pokemon image",
                modifier = Modifier
                    .align(Alignment.Center)
                    .zIndex(2f)
            )
        }
    }
}

@Composable
fun PokemonCardListItemInfo(
    pokemon: Pokemon,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = "Nº" + "%04d".format(pokemon.id),
            style = PokedexTheme.typography.subTitleMedium
        )
        Text(text = pokemon.name, style = PokedexTheme.typography.titleMedium)

        Spacer(modifier = Modifier.height(PokedexTheme.dimensions.spacingSmall))

        Row {
            pokemon.mainType?.let { mainType: PokemonTypeEnum -> TypePill(type = mainType) }
            pokemon.secondaryType?.let { secondaryType: PokemonTypeEnum ->
                Spacer(modifier = Modifier.width(PokedexTheme.dimensions.spacingXSmall))
                TypePill(type = secondaryType)
            }
        }
    }
}

@DefaultPreviews
@Composable
fun PokemonCardListItemPreview() {
    PokemonCardListItem(pokemon = PREVIEW_POKEMON, onClicked = {})
}