package nl.rhaydus.pokedex.features.pokemon_display.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import nl.rhaydus.pokedex.core.presentation.theme.PokedexTheme
import nl.rhaydus.pokedex.features.pokemon_display.domain.enums.PokemonTypeEnum

@Composable
fun TypePill(type: PokemonTypeEnum) {
    Box(
        modifier = Modifier
            .background(
                color = colorResource(id = type.colorId),
                shape = CircleShape
            )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(
                vertical = PokedexTheme.padding.xSmall,
                horizontal = PokedexTheme.padding.small
            )
        ) {
            Box(
                modifier = Modifier.background(
                    color = Color.White,
                    shape = CircleShape
                )
            ) {
                Image(
                    painter = painterResource(id = type.typeImage),
                    contentDescription = "desc",
                    modifier = Modifier
                        .padding(PokedexTheme.padding.xSmall)
                        .size(16.dp)
                        .align(Alignment.Center),
                    colorFilter = ColorFilter.tint(
                        color = colorResource(id = type.colorId)
                    )
                )
            }

            Spacer(modifier = Modifier.width(PokedexTheme.dimensions.spacingXSmall))

            Text(
                text = type.getName(),
                style = PokedexTheme.typography.typeText,
                color = type.getTextColor(),
                maxLines = 1
            )
        }
    }
}