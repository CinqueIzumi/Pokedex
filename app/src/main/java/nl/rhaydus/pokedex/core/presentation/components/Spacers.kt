package nl.rhaydus.pokedex.core.presentation.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun StatusBarSpacer(modifier: Modifier = Modifier) {
    Spacer(modifier = modifier.windowInsetsTopHeight(WindowInsets.statusBars))
}