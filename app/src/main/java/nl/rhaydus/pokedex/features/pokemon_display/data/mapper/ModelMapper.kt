package nl.rhaydus.pokedex.features.pokemon_display.data.mapper

import nl.rhaydus.pokedex.features.pokemon_display.data.network.response.IndividualPokemonFlavorTextResponse
import nl.rhaydus.pokedex.features.pokemon_display.data.network.response.IndividualPokemonResponse
import nl.rhaydus.pokedex.features.pokemon_display.data.network.response.IndividualPokemonSpeciesFlavorTextResponse
import nl.rhaydus.pokedex.features.pokemon_display.data.network.response.IndividualPokemonSpeciesResponse
import nl.rhaydus.pokedex.features.pokemon_display.domain.enums.PokemonTypeEnum
import nl.rhaydus.pokedex.features.pokemon_display.domain.model.Pokemon
import timber.log.Timber

fun IndividualPokemonFlavorTextResponse.toIndividualPokemonSpeciesFlavorText(): IndividualPokemonSpeciesFlavorTextResponse {
    val versionCode: Int = this.version.url
        .removePrefix("https://pokeapi.co/api/v2/version/")
        .removeSuffix("/")
        .toInt()
    return IndividualPokemonSpeciesFlavorTextResponse(
        // The replace is required due to the API delivering texts with line breaks
        text = this.flavorText.replace("\n", " "),
        language = this.language.name,
        versionCode = versionCode
    )
}

fun List<IndividualPokemonFlavorTextResponse>.getMostRecentFlavorText(): String {
    // Get a list of all the flavor texts
    val unfilteredList: List<IndividualPokemonSpeciesFlavorTextResponse> =
        this.map { it.toIndividualPokemonSpeciesFlavorText() }

    // Get the most recent english flavor text
    return unfilteredList.filter { it.language == "en" }
        .sortedByDescending { it.versionCode }[0].text
}

fun IndividualPokemonResponse.toPokemon(speciesResponse: IndividualPokemonSpeciesResponse): Pokemon {
    val mainType: String = this.types[0].type.name.uppercase()
    val secondaryType: String? =
        this.types.getOrNull(1)?.type?.name?.uppercase()
    val artworkUrl: String = this.sprites.other.officialArtwork.spriteUrl

    val weightString = "${this.weight / 10.0} kg"
    val heightString = "${this.height / 10.0} m"

    // ability-one, ability-two -> Ability One, Ability Two
    val abilityStringList = this.abilities.joinToString { ability ->
        ability.ability.name.split('-').joinToString(" ") { oldAbilityString ->
            oldAbilityString.replaceFirstChar { it.uppercase() }
        }
    }

    // Gender rate is given, based on the female percentage, in 1/8ths
    // So if genderRate = 1, then the female percentage is 12.5%, whereas male is 87.5%
    // If the pokemon is genderless, -1 is returned
    Timber.d("Percentage: ${speciesResponse.genderRate}")
    val malePercentage = if (speciesResponse.genderRate == -1) {
        -(1.0)
    } else {
        ((8 - speciesResponse.genderRate) * 100) / 8.0
    }

    return Pokemon(
        id = this.id,
        name = this.name.replaceFirstChar { it.uppercase() },
        mainType = PokemonTypeEnum.valueOf(mainType),
        secondaryType = secondaryType?.let { PokemonTypeEnum.valueOf(it) },
        artworkUrl = artworkUrl,
        weight = weightString,
        height = heightString,
        abilities = abilityStringList,
        malePercentage = malePercentage,
        description = speciesResponse.flavorTestEntries.getMostRecentFlavorText()
    )
}