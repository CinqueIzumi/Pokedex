package nl.rhaydus.pokedex.features.pokemon_display.domain.use_cases

import io.kotest.matchers.shouldBe
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import nl.rhaydus.pokedex.features.pokemon_display.domain.model.Pokemon
import nl.rhaydus.pokedex.features.pokemon_display.domain.repositories.PokemonRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class FavoritePokemonTest {
    private val mockPokemonRepository: PokemonRepository = mockk()
    private val mockPokemon: Pokemon = mockk()

    private val useCase: FavoritePokemon = FavoritePokemon(mockPokemonRepository)

    @BeforeEach
    fun init() {
        clearAllMocks()
    }

    @Test
    fun `calls repository to favorite pokemon`() {
        // ----- Arrange -----
        coEvery {
            mockPokemonRepository.favoritePokemon(any())
        }.returns(Result.success(Unit))

        // ----- Act -----
        val result = runBlocking { useCase(mockPokemon) }

        // ----- Assert -----
        result shouldBe Result.success(Unit)
    }
}