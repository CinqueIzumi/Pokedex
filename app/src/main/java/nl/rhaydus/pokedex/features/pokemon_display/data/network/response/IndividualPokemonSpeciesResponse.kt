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
    val language: IndividualSpecifiesFlavorTextLanguageResponse,
    @SerializedName("version")
    val version: IndividualSpecifiesFlavorTextVersionResponse
)

data class IndividualSpecifiesFlavorTextLanguageResponse(
    @SerializedName("name")
    val name: String,
)

data class IndividualSpecifiesFlavorTextVersionResponse(
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