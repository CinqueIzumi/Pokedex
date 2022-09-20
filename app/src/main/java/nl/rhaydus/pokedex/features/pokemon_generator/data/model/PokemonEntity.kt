package nl.rhaydus.pokedex.features.pokemon_generator.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PokemonEntity(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "poke_name") val pokeName: String,
    @ColumnInfo(name = "poke_image_url") val pokeImageUrl: String,
    @ColumnInfo(name = "poke_main_type") val pokeMainType: String
)