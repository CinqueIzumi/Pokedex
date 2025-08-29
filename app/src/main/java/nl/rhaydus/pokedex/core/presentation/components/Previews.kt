package nl.rhaydus.pokedex.core.presentation.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    name = "System default/Dark",
    showBackground = true,
    group = "Default light/dark"
)
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    name = "System default/Light",
    showBackground = true,
    group = "Default light/dark"
)
annotation class LightDarkPreviews

@Composable
fun DefaultPreviewSetup(content: @Composable ColumnScope.() -> Unit) {
    MaterialTheme {
        Column(
            content = content
        )
    }
}