package nl.rhaydus.pokedex.features.pokemon_generator.presentation.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.ramcosta.composedestinations.annotation.Destination
import nl.rhaydus.pokedex.features.pokemon_generator.domain.model.Pokemon

@Composable
@Destination
fun DetailedPokemonScreen(
    poke: Pokemon
) {
    Column {
        Text(poke.toString())
    }
}