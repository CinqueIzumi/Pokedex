package nl.rhaydus.pokedex.features.pokemon_display.data.model

import com.google.gson.annotations.SerializedName

data class PokemonApiModel(
    val name: String,
    val id: Int,
    val sprites: PokemonSpritesApiModel,
    val types: List<PokemonTypeEntryApiModel>,
    val weight: Double,
    val height: Double,
    val stats: List<PokemonStatsEntryApiModel>
)

data class PokemonSpritesApiModel(
    val other: PokemonSpriteOtherApiModel
)

data class PokemonSpriteOtherApiModel(
    @SerializedName("official-artwork")
    val artwork: PokemonSpriteOfficialArtApiModel
)

data class PokemonSpriteOfficialArtApiModel(
    @SerializedName("front_default")
    val artworkUrl: String?
)

data class PokemonTypeEntryApiModel(
    val slot: Int,
    val type: PokemonTypeApiModel
)

data class PokemonTypeApiModel(
    val name: String
)

data class PokemonStatsEntryApiModel(
    @SerializedName("base_stat")
    val baseStat: Int,
)