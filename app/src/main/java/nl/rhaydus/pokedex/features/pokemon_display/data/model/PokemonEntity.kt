package nl.rhaydus.pokedex.features.pokemon_display.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import nl.rhaydus.pokedex.features.pokemon_display.domain.enums.PokemonTypeEnum

@Entity
data class PokemonEntity(
    @PrimaryKey val id: Int,
    @ColumnInfo("poke_name") val name: String,
    @ColumnInfo("poke_main_type") val mainType: PokemonTypeEnum? = null,
    @ColumnInfo("poke_artwork_url") val artworkUrl: String? = null,
    @ColumnInfo("poke_weight") val weight: String? = null,
    @ColumnInfo("poke_height") val height: String? = null,
    @ColumnInfo("poke_abilities") val abilities: String? = null,
    @ColumnInfo("poke_description") val description: String? = null,
    @ColumnInfo("poke_male_percentage") val malePercentage: Double? = null,
    @ColumnInfo("poke_secondary_type") val secondaryType: PokemonTypeEnum? = null
)