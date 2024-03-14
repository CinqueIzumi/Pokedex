package nl.rhaydus.pokedex.features.pokemon_display.data.network.response

import com.google.gson.annotations.SerializedName

data class IndividualPokemonSpeciesResponse(
    @SerializedName("gender_rate")
    val genderRate: Int,
    @SerializedName("flavor_text_entries")
    val flavorTestEntries: List<IndividualPokemonFlavorTextResponse>
)

data class IndividualPokemonFlavorTextResponse(
    @SerializedName("flavor_text")
    val flavorText: String,
    @SerializedName("language")
    val language: IndividualSpeciesFlavorTextLanguageResponse,
    @SerializedName("version")
    val version: IndividualSpeciesFlavorTextVersionResponse
)

data class IndividualSpeciesFlavorTextLanguageResponse(
    @SerializedName("name")
    val name: String,
)

data class IndividualSpeciesFlavorTextVersionResponse(
    @SerializedName("url")
    val url: String
)

data class IndividualPokemonSpeciesFlavorTextResponse(
    @SerializedName("text")
    val text: String,
    @SerializedName("language")
    val language: String,
    @SerializedName("versionCode")
    val versionCode: Int,
)