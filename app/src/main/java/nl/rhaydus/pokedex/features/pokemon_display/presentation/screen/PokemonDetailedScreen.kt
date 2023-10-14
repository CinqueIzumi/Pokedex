package nl.rhaydus.pokedex.features.pokemon_display.presentation.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Female
import androidx.compose.material.icons.filled.Male
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.ramcosta.composedestinations.annotation.Destination
import nl.rhaydus.pokedex.R
import nl.rhaydus.pokedex.core.data.constant.PREVIEW_POKEMON
import nl.rhaydus.pokedex.core.presentation.navigation.BottomNavGraph
import nl.rhaydus.pokedex.core.presentation.theme.DefaultPreviews
import nl.rhaydus.pokedex.core.presentation.theme.PokedexTheme
import nl.rhaydus.pokedex.features.pokemon_display.domain.enums.PokemonTypeEnum
import nl.rhaydus.pokedex.features.pokemon_display.domain.model.Pokemon
import nl.rhaydus.pokedex.features.pokemon_display.presentation.component.TypePill
import nl.rhaydus.pokedex.features.pokemon_display.presentation.uievent.PokemonDisplayDetailedUiEvent
import nl.rhaydus.pokedex.features.pokemon_display.presentation.viewmodel.PokemonDetailedViewModel

@BottomNavGraph
@Destination
@Composable
fun PokemonDetailedScreen(
    pokemon: Pokemon,
    vm: PokemonDetailedViewModel = hiltViewModel()
) {
    val state by vm.pokemonDisplayScreenState.collectAsStateWithLifecycle()

    LaunchedEffect(true) {
        vm.handleEvent(PokemonDisplayDetailedUiEvent.InitializePokemon(pokemon = pokemon))
    }

    PokemonDetailedScreen(
        pokemon = state.pokemon,
        isLoading = state.isLoading,
    )
}

@Composable
fun PokemonDetailedScreen(
    pokemon: Pokemon?,
    isLoading: Boolean,
) {
    Scaffold {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            if (isLoading) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
            pokemon?.let {
                Column {
                    PokemonDetailedImage(pokemon = pokemon)

                    Column(modifier = Modifier.padding(PokedexTheme.padding.medium)) {
                        PokemonDetailedSummary(pokemon = pokemon)

                        Spacer(modifier = Modifier.height(PokedexTheme.dimensions.spacingMedium))

                        Row {
                            pokemon.weight?.let {
                                DetailedCategorySection(
                                    categoryName = stringResource(id = R.string.detailed_pokemon_category_weight),
                                    categoryIconId = R.drawable.ic_weight,
                                    categoryBodyText = pokemon.weight,
                                    modifier = Modifier.weight(1f)
                                )
                            }

                            pokemon.height?.let {
                                Spacer(modifier = Modifier.width(PokedexTheme.dimensions.spacingLarge))

                                DetailedCategorySection(
                                    categoryName = stringResource(id = R.string.detailed_pokemon_category_height),
                                    categoryIconId = R.drawable.ic_height,
                                    categoryBodyText = pokemon.height,
                                    modifier = Modifier.weight(1f)
                                )
                            }
                        }

                        pokemon.abilities?.let {
                            Spacer(modifier = Modifier.height(PokedexTheme.dimensions.spacingLarge))

                            Row {
                                DetailedCategorySection(
                                    categoryName = stringResource(id = R.string.detailed_pokemon_category_abilities),
                                    categoryIconId = R.drawable.ic_pokeball,
                                    categoryBodyText = pokemon.abilities,
                                    modifier = Modifier.weight(1f)
                                )
                            }
                        }

                        DetailedGenderPercentageView(pokemon = pokemon)
                    }
                }
            }
        }
    }
}

@Composable
private fun DetailedGenderPercentageView(pokemon: Pokemon) {
    pokemon.malePercentage?.let {
        Column {
            Spacer(modifier = Modifier.height(PokedexTheme.dimensions.spacingLarge))

            Text(
                text = stringResource(id = R.string.detailed_pokemon_category_gender),
                style = PokedexTheme.typography.categoryText,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(PokedexTheme.dimensions.spacingSmall))

            LinearProgressIndicator(
                progress = (pokemon.malePercentage / 100).toFloat(),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                    // This clip is used as a replacement for StrokeCap, as it also modifies the middle part
                    .clip(RoundedCornerShape(8.dp)),
                color = colorResource(id = R.color.color_gender_male),
                trackColor = colorResource(id = R.color.color_gender_female),
            )

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        Icons.Filled.Male,
                        contentDescription = null,
                        tint = Color.Black.copy(alpha = 0.7f)
                    )

                    Spacer(modifier = Modifier.width(PokedexTheme.dimensions.spacingXSmall))

                    Text(text = "${pokemon.malePercentage}%")
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        Icons.Filled.Female,
                        contentDescription = null,
                        tint = Color.Black.copy(alpha = 0.7f)
                    )

                    Spacer(modifier = Modifier.width(PokedexTheme.dimensions.spacingXSmall))

                    Text(text = "${100 - pokemon.malePercentage}%")
                }
            }
        }
    }
}
@Composable
private fun DetailedCategorySection(
    categoryName: String,
    categoryIconId: Int,
    categoryBodyText: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(PokedexTheme.dimensions.spacingXSmall)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(PokedexTheme.dimensions.spacingSmall)
        ) {
            Icon(
                painterResource(id = categoryIconId),
                contentDescription = null
            )
            Text(text = categoryName, style = PokedexTheme.typography.categoryText)
        }

        Row {
            Box(
                modifier = Modifier
                    .border(
                        1.dp,
                        color = Color.Black.copy(alpha = 0.1f),
                        shape = RoundedCornerShape(16.dp)
                    )
                    .padding(8.dp)
                    .weight(1f)
            ) {
                Text(
                    text = categoryBodyText,
                    modifier = Modifier.align(Alignment.Center),
                    style = PokedexTheme.typography.categoryBodyText
                )
            }
        }
    }
}

@Composable
private fun PokemonDetailedImage(pokemon: Pokemon) {
    pokemon.mainType?.let { mainType: PokemonTypeEnum ->
        val screenWidth: Int = LocalConfiguration.current.screenWidthDp

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.4f)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.9f)
                    .background(
                        color = colorResource(id = mainType.colorId),
                        shape = RoundedCornerShape(
                            bottomEnd = (screenWidth / 2).dp,
                            bottomStart = (screenWidth / 2).dp
                        )
                    )
                    .fillMaxHeight(0.5f)
            ) {
                Image(
                    painter = painterResource(mainType.typeImage),
                    contentDescription = "desc",
                    modifier = Modifier
                        .size(200.dp)
                        .zIndex(1f)
                        .align(Alignment.Center),
                    colorFilter = ColorFilter.tint(color = Color.White.copy(alpha = 0.7f))
                )
            }
            AsyncImage(
                model = pokemon.artworkUrl,
                contentDescription = "poke image",
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .zIndex(2f)
            )
        }
    }
}

@Composable
private fun PokemonDetailedSummary(pokemon: Pokemon) {
    Text(text = pokemon.name, style = PokedexTheme.typography.titleLarge)
    Text(
        text = "Nº" + "%04d".format(pokemon.id),
        style = PokedexTheme.typography.subTitleLarge
    )

    Spacer(modifier = Modifier.height(PokedexTheme.dimensions.spacingLarge))

    Row {
        pokemon.mainType?.let { TypePill(type = pokemon.mainType) }
        pokemon.secondaryType?.let { type ->
            Spacer(modifier = Modifier.width(PokedexTheme.dimensions.spacingSmall))

            TypePill(type = type)
        }
    }

    Spacer(modifier = Modifier.height(PokedexTheme.dimensions.spacingLarge))

    pokemon.description?.let {
        Text(
            text = pokemon.description,
            style = PokedexTheme.typography.bodyText
        )
        Spacer(modifier = Modifier.height(PokedexTheme.dimensions.spacingMedium))
    }

    Divider(color = Color(0xFF000000).copy(alpha = 0.05f))
}

@DefaultPreviews
@Composable
fun PokemonDetailedScreenPreview() {
    PokemonDetailedScreen(
        pokemon = PREVIEW_POKEMON,
        isLoading = false
    )
}