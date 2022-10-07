package nl.rhaydus.pokedex.features.pokemon_display.presentation.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import nl.rhaydus.pokedex.core.PokedexHelper
import javax.inject.Inject

@HiltViewModel
class DetailedPokemonScreenViewModel @Inject constructor(
    private val pokedexHelper: PokedexHelper
) : ViewModel() {

    fun getPokemonStatColor(stat: String): Int {
        return pokedexHelper.determineStatColor(stat)
    }

    fun getPokemonTypeColor(type: String): Int {
        return pokedexHelper.determineTypeColor(type)
    }
}