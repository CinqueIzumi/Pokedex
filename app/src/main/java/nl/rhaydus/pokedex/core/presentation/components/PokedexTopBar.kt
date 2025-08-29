package nl.rhaydus.pokedex.core.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import nl.rhaydus.pokedex.R

@Composable
fun PokedexTopBar(
    title: String,
    onBackButtonClick: (() -> Unit)? = null,
) {
    val iconSize = remember { 32.dp }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp),
    ) {
        when {
            onBackButtonClick == null -> {
                Spacer(modifier = Modifier.width(iconSize))
            }

            else -> {
                Icon(
                    painter = painterResource(R.drawable.ic_chevron_back),
                    contentDescription = "Back button",
                    modifier = Modifier
                        .size(iconSize)
                        .noRippleClickable(onBackButtonClick)
                )
            }
        }

        Spacer(modifier = Modifier.width(24.dp))

        Text(
            text = title,
            style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold)
        )
    }
}

@LightDarkPreviews
@Composable
private fun PokedexTopBarPreview() {
    DefaultPreviewSetup {
        Column {
            PokedexTopBar(title = "Pokedex")

            PokedexTopBar(title = "Pokedex", onBackButtonClick = {})
        }
    }
}