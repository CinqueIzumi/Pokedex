package nl.rhaydus.pokedex.features.pokemon_display.domain.use_cases

import io.kotest.matchers.shouldBe
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import nl.rhaydus.pokedex.features.pokemon_display.domain.model.Pokemon
import nl.rhaydus.pokedex.features.pokemon_display.domain.repositories.PokemonRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class GetPokemonWithFilterTest {
    private val mockPokemonRepository: PokemonRepository = mockk()
    private val mockPokemon: Pokemon = mockk()

    private val pokemonList: List<Pokemon> = listOf(mockPokemon, mockPokemon, mockPokemon)
    private val useCase: GetPokemonWithFilter = GetPokemonWithFilter(mockPokemonRepository)

    @BeforeEach
    fun init() {
        clearAllMocks()
    }

    @Test
    fun `calls repository for data`() {
        // ----- Arrange -----
        coEvery {
            mockPokemonRepository.getPokemonWithFilter(
                any(), any(), any(), any()
            )
        }.returns(Result.success(pokemonList))

        // ----- Act -----
        val result = runBlocking { useCase() }

        // ----- Assert -----
        result shouldBe Result.success(pokemonList)
        coVerify { mockPokemonRepository.getPokemonWithFilter(any(), any(), any(), any()) }
    }
}