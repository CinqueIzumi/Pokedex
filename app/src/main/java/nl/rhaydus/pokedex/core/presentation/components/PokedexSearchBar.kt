package nl.rhaydus.pokedex.core.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun PokedexSearchBar(
    value: String,
    onValueChange: (String) -> Unit,
    placeholderText: String = "Search",
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        shape = CircleShape,
        modifier = Modifier.fillMaxWidth(),
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search icon",
            )
        },
        colors = OutlinedTextFieldDefaults.colors().copy(
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
        ),
        placeholder = { Text(text = placeholderText) }
    )
}