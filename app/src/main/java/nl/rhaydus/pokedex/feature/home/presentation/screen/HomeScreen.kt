package nl.rhaydus.pokedex.feature.home.presentation.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
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
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.generated.destinations.TypeScreenDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.spec.Direction
import nl.rhaydus.pokedex.R
import nl.rhaydus.pokedex.core.presentation.components.DefaultPreviewSetup
import nl.rhaydus.pokedex.core.presentation.components.LightDarkPreviews
import nl.rhaydus.pokedex.core.presentation.components.PokedexSearchBar
import nl.rhaydus.pokedex.core.presentation.components.StatusBarSpacer
import nl.rhaydus.pokedex.core.presentation.theme.POKEDEX_BACKGROUND
import nl.rhaydus.pokedex.core.presentation.theme.POKEDEX_BLUE
import nl.rhaydus.pokedex.core.presentation.theme.POKEDEX_GREEN
import nl.rhaydus.pokedex.core.presentation.theme.POKEDEX_ORANGE
import nl.rhaydus.pokedex.core.presentation.theme.POKEDEX_RED

@Destination<RootGraph>(start = true)
@Composable
fun HomeScreen(
    navigator: DestinationsNavigator,
) {
    HomeScreen(navigateTo = navigator::navigate)
}

@Composable
private fun HomeScreen(
    navigateTo: (Direction) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = POKEDEX_RED)
    ) {
        StatusBarSpacer()

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                painter = painterResource(R.drawable.pokeball),
                contentDescription = "Pokeball icon",
                modifier = Modifier
                    .size(24.dp)
                    .rotate(-45f),
                tint = Color.White,
            )

            Spacer(modifier = Modifier.width(16.dp))

            Text(
                text = "Pokedex",
                modifier = Modifier.weight(1f),
                style = MaterialTheme.typography.titleLarge,
                color = Color.White,
            )
        }

        Column(modifier = Modifier.weight(1f)) {
            Box {
                Image(
                    painter = painterResource(R.drawable.pokeball),
                    contentDescription = "Pokeball background image",
                    modifier = Modifier
                        .fillMaxWidth()
                        .alpha(0.15f)
                        .rotate(-45f),
                    colorFilter = ColorFilter.tint(color = Color.White),
                    contentScale = ContentScale.FillWidth
                )

                Column(
                    modifier = Modifier
                        .padding(all = 24.dp)
                        .fillMaxHeight(),
                    verticalArrangement = Arrangement.Center,
                ) {
                    Text(
                        text = "Find your favorite Pokemon",
                        color = Color.White,
                        style = MaterialTheme.typography.displaySmall,
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    PokedexSearchBar(
                        value = "",
                        onValueChange = {},
                    )
                }
            }
        }

        BoxWithConstraints(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    color = POKEDEX_BACKGROUND,
                    shape = TopArcShape(),
                )
                .weight(1f),
        ) {
            Column(
                modifier = Modifier
                    .padding(top = maxHeight * 0.1f)
                    .padding(horizontal = 24.dp)
                    .padding(bottom = 24.dp)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.SpaceEvenly,
            ) {
                NavigationButton(
                    title = "Types",
                    buttonColor = POKEDEX_GREEN,
                    onClick = { navigateTo(TypeScreenDestination) },
                )

                NavigationButton(
                    title = "Locations",
                    buttonColor = POKEDEX_ORANGE,
                    onClick = {}
                )

                NavigationButton(
                    title = "Moves and Abilities",
                    buttonColor = POKEDEX_BLUE,
                    onClick = {}
                )

                NavigationButton(
                    title = "Favorites",
                    buttonColor = POKEDEX_RED,
                    onClick = {}
                )
            }
        }
    }
}

@Composable
private fun NavigationButton(
    title: String,
    buttonColor: Color,
    onClick: () -> Unit,
) {
    FilledTonalButton(
        onClick = onClick,
        colors = ButtonDefaults.filledTonalButtonColors().copy(
            containerColor = buttonColor,
        ),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge,
                color = Color.White,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center,
            )
        }
    }
}

private class TopArcShape : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density,
    ): Outline {
        val path = Path().apply {
            moveTo(0f, size.height * 0.1f)

            quadraticTo(
                size.width / 2, -size.height * 0.05f,
                size.width, size.height * 0.1f
            )

            lineTo(size.width, size.height)

            lineTo(0f, size.height)

            close()
        }
        return Outline.Generic(path)
    }
}

@Composable
@LightDarkPreviews
private fun HomeScreenPreview(modifier: Modifier = Modifier) {
    DefaultPreviewSetup {
        HomeScreen(navigateTo = {})
    }
}