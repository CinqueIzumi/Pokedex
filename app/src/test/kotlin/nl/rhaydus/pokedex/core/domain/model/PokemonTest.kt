package nl.rhaydus.pokedex.core.domain.model

import io.kotest.matchers.shouldBe
import nl.rhaydus.pokedex.features.pokemon_display.domain.enums.PokemonTypeEnum
import org.junit.jupiter.api.Test

class PokemonTest {
    private val completePokemon = Pokemon(
        id = 1,
        name = "Bulbasaur",
        mainType = PokemonTypeEnum.GRASS,
        secondaryType = PokemonTypeEnum.POISON,
        artworkUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/1.png",
        weight = "6.9 kg",
        height = "0.7 m",
        abilities = "One, Two, Three",
        description = "For some time after its birth, it grows by taking nourishment from the seed on its back. ",
        malePercentage = 50.0,
        favorite = false
    )


    @Test
    fun `isComplete should return false if mainType was null`() {
        // ----- Arrange -----
        val pokemonToTest = completePokemon.copy(mainType = null)

        // ----- Act -----
        val isComplete = pokemonToTest.isComplete()

        // ----- Assert -----
        isComplete shouldBe false
    }

    @Test
    fun `isComplete should return false if artworkUrl was null`() {
        // ----- Arrange -----
        val pokemonToTest = completePokemon.copy(artworkUrl = null)

        // ----- Act -----
        val isComplete = pokemonToTest.isComplete()

        // ----- Assert -----
        isComplete shouldBe false
    }

    @Test
    fun `isComplete should return false if weight was null`() {
        // ----- Arrange -----
        val pokemonToTest = completePokemon.copy(weight = null)

        // ----- Act -----
        val isComplete = pokemonToTest.isComplete()

        // ----- Assert -----
        isComplete shouldBe false
    }

    @Test
    fun `isComplete should return false if height was null`() {
        // ----- Arrange -----
        val pokemonToTest = completePokemon.copy(height = null)

        // ----- Act -----
        val isComplete = pokemonToTest.isComplete()

        // ----- Assert -----
        isComplete shouldBe false
    }

    @Test
    fun `isComplete should return false if abilities was null`() {
        // ----- Arrange -----
        val pokemonToTest = completePokemon.copy(abilities = null)

        // ----- Act -----
        val isComplete = pokemonToTest.isComplete()

        // ----- Assert -----
        isComplete shouldBe false
    }

    @Test
    fun `isComplete should return false if description was null`() {
        // ----- Arrange -----
        val pokemonToTest = completePokemon.copy(mainType = null)

        // ----- Act -----
        val isComplete = pokemonToTest.isComplete()

        // ----- Assert -----
        isComplete shouldBe false
    }

    @Test
    fun `isComplete should return false if male percentage was null`() {
        // ----- Arrange -----
        val pokemonToTest = completePokemon.copy(malePercentage = null)

        // ----- Act -----
        val isComplete = pokemonToTest.isComplete()

        // ----- Assert -----
        isComplete shouldBe false
    }

    @Test
    fun `isComplete should return true if only secondary type was null was null`() {
        // ----- Arrange -----
        val pokemonToTest = completePokemon.copy(secondaryType = null)

        // ----- Act -----
        val isComplete = pokemonToTest.isComplete()

        // ----- Assert -----
        isComplete shouldBe true
    }

    @Test
    fun `isComplete should return true if pokemon was complete`() {
        // ----- Act -----
        val isComplete = completePokemon.isComplete()

        // ----- Assert -----
        isComplete shouldBe true
    }
}

