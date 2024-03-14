package nl.rhaydus.pokedex.features.pokemon_display.data.mapper

import io.kotest.matchers.shouldBe
import io.mockk.clearAllMocks
import nl.rhaydus.pokedex.core.domain.model.Pokemon
import nl.rhaydus.pokedex.features.pokemon_display.data.network.response.IndividualAbilityResponse
import nl.rhaydus.pokedex.features.pokemon_display.data.network.response.IndividualPokemonFlavorTextResponse
import nl.rhaydus.pokedex.features.pokemon_display.data.network.response.IndividualPokemonResponse
import nl.rhaydus.pokedex.features.pokemon_display.data.network.response.IndividualPokemonSpeciesFlavorTextResponse
import nl.rhaydus.pokedex.features.pokemon_display.data.network.response.IndividualPokemonSpeciesResponse
import nl.rhaydus.pokedex.features.pokemon_display.data.network.response.IndividualSpeciesFlavorTextLanguageResponse
import nl.rhaydus.pokedex.features.pokemon_display.data.network.response.IndividualSpeciesFlavorTextVersionResponse
import nl.rhaydus.pokedex.features.pokemon_display.data.network.response.IndividualTypeResponse
import nl.rhaydus.pokedex.features.pokemon_display.data.network.response.PokemonAbilityResponse
import nl.rhaydus.pokedex.features.pokemon_display.data.network.response.PokemonSpriteOfficialArtworkResponse
import nl.rhaydus.pokedex.features.pokemon_display.data.network.response.PokemonSpritesOtherResponse
import nl.rhaydus.pokedex.features.pokemon_display.data.network.response.PokemonSpritesResponse
import nl.rhaydus.pokedex.features.pokemon_display.data.network.response.PokemonTypeResponse
import nl.rhaydus.pokedex.features.pokemon_display.domain.enums.PokemonTypeEnum
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class ModelMapperTest {
    @BeforeEach
    fun setUp() {
        clearAllMocks()
    }

    @Test
    fun `IndividualPokemonFlavorTextResponse should be mapped to IndividualPokemonSpeciesFlavorText properly`() {
        // ----- Arrange -----
        val versionCode = 20
        val versionUrlString = "https://pokeapi.co/api/v2/version/$versionCode/"
        val speciesFlavorTextVersionResponse =
            IndividualSpeciesFlavorTextVersionResponse(url = versionUrlString)

        val languageName = "en"
        val individualSpeciesFlavorTextLanguageResponse =
            IndividualSpeciesFlavorTextLanguageResponse(name = languageName)

        val flavorText = "this is a long flavor text \nwith paragraph breaks"

        val individualPokemonFlavorTextResponse = IndividualPokemonFlavorTextResponse(
            version = speciesFlavorTextVersionResponse,
            flavorText = flavorText,
            language = individualSpeciesFlavorTextLanguageResponse
        )

        val expectedText = flavorText.replace("\n", " ")

        val expectedIndividualPokemonSpeciesFlavorTextResponse =
            IndividualPokemonSpeciesFlavorTextResponse(
                text = expectedText,
                language = languageName,
                versionCode = versionCode
            )

        // ----- Act -----
        val result = individualPokemonFlavorTextResponse.toIndividualPokemonSpeciesFlavorText()

        // ----- Assert -----
        result shouldBe expectedIndividualPokemonSpeciesFlavorTextResponse
    }

    @Test
    fun `getMostRecentFlavorText returns most recent english flavor text`() {
        // ----- Arrange -----
        val fillerText = "This is a filler text"
        val expectedResult = "This is the expected result"

        val dutchLanguage = "nl"
        val englishLanguage = "en"

        val dutchHighest = getIndividualPokemonFlavorTextResponse(
            flavorText = fillerText,
            language = dutchLanguage,
            versionCode = 30
        )
        val englishHighest = getIndividualPokemonFlavorTextResponse(
            flavorText = expectedResult,
            language = englishLanguage,
            versionCode = 29
        )
        val englishSecondHighest = getIndividualPokemonFlavorTextResponse(
            flavorText = fillerText,
            language = englishLanguage,
            versionCode = 5
        )
        val englishLowest = getIndividualPokemonFlavorTextResponse(
            flavorText = fillerText,
            language = englishLanguage,
            versionCode = 2
        )

        val responseList = listOf(dutchHighest, englishLowest, englishHighest, englishSecondHighest)

        // ----- Act -----
        val result = responseList.getMostRecentFlavorText()

        // ----- Assert -----
        result shouldBe expectedResult
    }

    @Test
    fun `IndividualPokemonResponse should be mapped to Pokemon properly`() {
        // ----- Arrange -----
        val typeApiName = "water"
        val expectedType = PokemonTypeEnum.WATER

        val weightApiValue = 25
        val expectedWeightString = "2.5 kg"

        val heightApiValue = 7
        val expectedHeightString = "0.7 m"

        val firstAbilityStringApi = "torrent"
        val secondAbilityStringApi = "rain-dish"
        val expectedAbilityString = "Torrent, Rain Dish"

        val genderRateApiValue = 1
        val expectedMalePercentage = 87.5

        val pokemonNameApi = "blastoise"
        val expectedPokemonName = "Blastoise"

        val pokemonId = 9
        val expectedArtworkUrl = "https://link/to/image"
        val expectedFlavorString = "This is the expected flavor string"

        val individualPokemonSpeciesResponse = IndividualPokemonSpeciesResponse(
            genderRate = genderRateApiValue,
            flavorTestEntries = listOf(
                IndividualPokemonFlavorTextResponse(
                    flavorText = expectedFlavorString,
                    language = IndividualSpeciesFlavorTextLanguageResponse(name = "en"),
                    version = IndividualSpeciesFlavorTextVersionResponse(url = "https://pokeapi.co/api/v2/version/20/")
                )
            )
        )
        val individualPokemonResponse = IndividualPokemonResponse(
            id = pokemonId,
            name = pokemonNameApi,
            types = listOf(PokemonTypeResponse(type = IndividualTypeResponse(name = typeApiName))),
            sprites = PokemonSpritesResponse(
                other = PokemonSpritesOtherResponse(
                    officialArtwork = PokemonSpriteOfficialArtworkResponse(
                        spriteUrl = expectedArtworkUrl
                    )
                )
            ),
            weight = weightApiValue,
            height = heightApiValue,
            abilities = listOf(
                PokemonAbilityResponse(ability = IndividualAbilityResponse(name = firstAbilityStringApi)),
                PokemonAbilityResponse(ability = IndividualAbilityResponse(name = secondAbilityStringApi))
            )
        )

        val expectedResult = Pokemon(
            id = pokemonId,
            name = expectedPokemonName,
            mainType = expectedType,
            artworkUrl = expectedArtworkUrl,
            weight = expectedWeightString,
            height = expectedHeightString,
            abilities = expectedAbilityString,
            description = expectedFlavorString,
            malePercentage = expectedMalePercentage,
            secondaryType = null
        )

        // ----- Act -----
        val result =
            individualPokemonResponse.toPokemon(speciesResponse = individualPokemonSpeciesResponse)

        // ----- Assert -----
        result shouldBe expectedResult
    }

    private fun getIndividualPokemonFlavorTextResponse(
        flavorText: String,
        language: String,
        versionCode: Int
    ): IndividualPokemonFlavorTextResponse {
        return IndividualPokemonFlavorTextResponse(
            flavorText = flavorText,
            language = IndividualSpeciesFlavorTextLanguageResponse(name = language),
            version = IndividualSpeciesFlavorTextVersionResponse(url = "https://pokeapi.co/api/v2/version/$versionCode/")
        )
    }
}