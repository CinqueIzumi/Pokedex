package nl.rhaydus.pokedex.features.splash.data.mapper

import io.kotest.matchers.shouldBe
import nl.rhaydus.pokedex.core.domain.model.Pokemon
import nl.rhaydus.pokedex.features.splash.data.network.response.GlobalPokemonInformationResponse
import nl.rhaydus.pokedex.features.splash.data.network.response.GlobalPokemonResponse
import org.junit.jupiter.api.Test

class PokemonInfoMapperTest {
    private val expectedId = 1
    private val expectedName = "Bulbasaur"

    private val infoResponse = GlobalPokemonInformationResponse(
        name = expectedName,
        url = "https://pokeapi.co/api/v2/pokemon/$expectedId/"
    )

    private val pokemon = Pokemon(name = expectedName, id = expectedId)

    @Test
    fun `GlobalPokemonInformationResponse should be mapped to pokemon successfully`() {
        // ----- Act -----
        val result = infoResponse.toPokemon()

        // ----- Assert -----
        result shouldBe pokemon
    }

    @Test
    fun `GlobalPokemonResponse should be mapped to list of pokemon successfully`() {
        // ----- Arrange -----
        val response = GlobalPokemonResponse(
            count = 3,
            pokemon = listOf(infoResponse, infoResponse, infoResponse)
        )
        val expectedResult = listOf(pokemon, pokemon, pokemon)

        // ----- Act -----
        val result = response.toPokemonList()

        // ----- Assert -----
        result shouldBe expectedResult
    }
}