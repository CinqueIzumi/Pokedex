package nl.rhaydus.pokedex.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import nl.rhaydus.pokedex.features.pokemon_display.domain.enums.PokemonSortEnum
import nl.rhaydus.pokedex.features.pokemon_display.domain.enums.PokemonTypeEnum

@Parcelize
data class Pokemon(
    val id: Int,
    val name: String,
    val favorite: Boolean,
    val mainType: PokemonTypeEnum? = null,
    val artworkUrl: String? = null,
    val weight: String? = null,
    val height: String? = null,
    val abilities: String? = null,
    val description: String? = null,
    val malePercentage: Double? = null,
    val secondaryType: PokemonTypeEnum? = null
) : Parcelable {
    fun isComplete(): Boolean {
        // The only field which could be nullable is secondary type if the pokemon is complete
        return (mainType != null && artworkUrl != null && weight != null && height != null
                && abilities != null && description != null && malePercentage != null)
    }
}

fun List<Pokemon>.handlePokemonOrder(sortType: PokemonSortEnum?): List<Pokemon> = when (sortType) {
    PokemonSortEnum.NUMBER_ASCENDING -> this.sortedBy { pokemon: Pokemon -> pokemon.id }
    PokemonSortEnum.NUMBER_DESCENDING -> this.sortedByDescending { pokemon: Pokemon -> pokemon.id }
    PokemonSortEnum.NAME_ASCENDING -> this.sortedBy { pokemon: Pokemon -> pokemon.name }
    PokemonSortEnum.NAME_DESCENDING -> this.sortedByDescending { pokemon: Pokemon -> pokemon.name }
    else -> this
}