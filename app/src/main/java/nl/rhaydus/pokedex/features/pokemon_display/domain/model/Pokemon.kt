package nl.rhaydus.pokedex.features.pokemon_display.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Pokemon(
    val name: String,
    val id: Int,
    val imageUrl: String,
    val types: List<String>,
    val weight: Double, // In hectogram -> 1 hectogram = 100 gr = 0,1 kg
    val height: Double, // In decimetres -> 1 dm = 10 cm = 0,1 m
    val hpStat: Int,
    val atkStat: Int,
    val defStat: Int,
    val spAtkStat: Int,
    val spDefStat: Int,
    val spdStat: Int,
    val favorite: Int, // 0 = false, 1 = true
) : Parcelable