package nl.rhaydus.pokedex.features.pokemon_generator.data.model

import com.google.gson.annotations.SerializedName

data class PokemonApiModel(
    val name: String,
    val id: Int,
    val sprites: PokemonSpritesApiModel
)

data class PokemonSpritesApiModel(
    @SerializedName("front_default")
    val url: String,
)