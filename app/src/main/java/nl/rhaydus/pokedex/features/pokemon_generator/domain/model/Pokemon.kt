package nl.rhaydus.pokedex.features.pokemon_generator.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Pokemon(
    val name: String,
    val id: Int,
    val imageUrl: String,
    val mainType: String,
) : Parcelable