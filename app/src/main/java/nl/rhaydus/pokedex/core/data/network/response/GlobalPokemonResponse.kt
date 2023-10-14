package nl.rhaydus.pokedex.core.data.network.response

import com.google.gson.annotations.SerializedName

data class GlobalPokemonResponse(
    @SerializedName("count")
    val count: Int,
    @SerializedName("results")
    val pokemon: List<GlobalPokemonInformationResponse>
)

data class GlobalPokemonInformationResponse(
    @SerializedName("name")
    val name: String,
    @SerializedName("url")
    val url: String
)