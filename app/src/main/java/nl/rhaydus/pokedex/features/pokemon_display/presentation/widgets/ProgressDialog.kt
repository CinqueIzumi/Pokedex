package nl.rhaydus.pokedex.features.pokemon_display.presentation.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import nl.rhaydus.pokedex.R

@Composable
fun ShowProgressDialog() {
    Dialog(
        onDismissRequest = {},
        DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false)
    ) {
        Box(
            modifier = Modifier
                .size(100.dp)
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(dimensionResource(id = R.dimen.dialog_corners))
                ),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }
}