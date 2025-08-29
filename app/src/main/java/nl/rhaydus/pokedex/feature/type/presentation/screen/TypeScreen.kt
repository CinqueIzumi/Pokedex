package nl.rhaydus.pokedex.feature.type.presentation.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import nl.rhaydus.pokedex.core.presentation.components.DefaultPreviewSetup
import nl.rhaydus.pokedex.core.presentation.components.LightDarkPreviews
import nl.rhaydus.pokedex.core.presentation.components.PokedexSearchBar
import nl.rhaydus.pokedex.core.presentation.components.PokedexTopBar
import nl.rhaydus.pokedex.core.presentation.components.StatusBarSpacer
import nl.rhaydus.pokedex.core.presentation.theme.POKEDEX_BACKGROUND
import nl.rhaydus.pokedex.feature.type.domain.model.PokemonType
import nl.rhaydus.pokedex.feature.type.presentation.event.TypeScreenUiEvent
import nl.rhaydus.pokedex.feature.type.presentation.state.TypeScreenUiState
import nl.rhaydus.pokedex.feature.type.presentation.viewmodel.TypeScreenViewModel

@Destination<RootGraph>
@Composable
fun TypeScreen(
    navigator: DestinationsNavigator,
    vm: TypeScreenViewModel = hiltViewModel(),
) {
    val state by vm.state.collectAsStateWithLifecycle()

    TypeScreen(
        state = state,
        onNavigateUp = navigator::popBackStack,
        onEvent = vm::onEvent,
    )
}

@Composable
private fun TypeScreen(
    state: TypeScreenUiState,
    onNavigateUp: () -> Unit,
    onEvent: (TypeScreenUiEvent) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = POKEDEX_BACKGROUND)
    ) {
        StatusBarSpacer()

        PokedexTopBar(
            title = "Types",
            onBackButtonClick = onNavigateUp,
        )

        Spacer(modifier = Modifier.height(8.dp))

        Column(
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            PokedexSearchBar(
                value = state.searchText,
                onValueChange = {
                    onEvent(TypeScreenUiEvent.OnSearchTextChanged(it))
                },
            )

            Spacer(modifier = Modifier.height(16.dp))

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                items(state.types) {
                    TypeButton(type = it)
                }
            }
        }
    }
}

@Composable
private fun TypeButton(type: PokemonType) {
    FilledTonalButton(
        onClick = {},
        colors = ButtonDefaults.filledTonalButtonColors().copy(containerColor = type.color),
        contentPadding = PaddingValues(all = 0.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
        ) {
            Text(
                text = type.name.lowercase().replaceFirstChar { it.uppercase() },
                modifier = Modifier
                    .padding(
                        vertical = 20.dp,
                        horizontal = 16.dp
                    )
                    .align(alignment = Alignment.CenterStart),
                color = Color.White,
                style = MaterialTheme.typography.bodyLarge,
            )

            Image(
                painter = painterResource(id = type.iconResource),
                contentDescription = "Type icon",
                modifier = Modifier
                    .align(alignment = Alignment.CenterEnd)
                    .alpha(0.2f),
                colorFilter = ColorFilter.tint(color = Color.White),
                contentScale = ContentScale.Fit
            )
        }
    }
}

@LightDarkPreviews
@Composable
private fun TypeScreenPreview() {
    DefaultPreviewSetup {
        TypeScreen(
            state = TypeScreenUiState(),
            onEvent = {},
            onNavigateUp = {},
        )
    }
}