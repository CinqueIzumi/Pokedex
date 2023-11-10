package nl.rhaydus.pokedex.features.pokemon_display.data.network.response

import com.google.gson.annotations.SerializedName
data class IndividualPokemonResponse(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("types")
    val types: List<PokemonTypeResponse>,
    @SerializedName("sprites")
    val sprites: PokemonSpritesResponse,
    @SerializedName("weight")
    val weight: Int,
    @SerializedName("height")
    val height: Int,
    @SerializedName("abilities")
    val abilities: List<PokemonAbilityResponse>
)

data class PokemonAbilityResponse(
    @SerializedName("ability")
    val ability: IndividualAbilityResponse
)

data class IndividualAbilityResponse(
    @SerializedName("name")
    val name: String
)

data class PokemonTypeResponse(
    @SerializedName("type")
    val type: IndividualTypeResponse
)

data class IndividualTypeResponse(
    @SerializedName("name")
    val name: String
)

data class PokemonSpritesResponse(
    @SerializedName("other")
    val other: PokemonSpritesOtherResponse
)

data class PokemonSpritesOtherResponse(
    @SerializedName("official-artwork")
    val officialArtwork: PokemonSpriteOfficialArtworkResponse
)

data class PokemonSpriteOfficialArtworkResponse(
    @SerializedName("front_default")
    val spriteUrl: String
)