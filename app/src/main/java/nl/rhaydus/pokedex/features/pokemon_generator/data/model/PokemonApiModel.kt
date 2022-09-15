package nl.rhaydus.pokedex.features.pokemon_generator.data.model

import com.google.gson.annotations.SerializedName

data class PokemonApiModel(
    val name: String,
    val id: Int,
    val sprites: PokemonSpritesApiModel,
    val types: List<PokemonTypeEntryApiModel>
)

data class PokemonSpritesApiModel(
    @SerializedName("front_default")
    val url: String,
)

data class PokemonTypeEntryApiModel(
    val slot: Int,
    val type: PokemonTypeApiModel
)
data class PokemonTypeApiModel(
    val name: String
)