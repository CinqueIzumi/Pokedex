package nl.rhaydus.pokedex.features.pokemon_display.domain.usecase

import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import kotlinx.coroutines.runBlocking
import nl.rhaydus.pokedex.core.domain.model.Pokemon
import nl.rhaydus.pokedex.features.pokemon_display.domain.repositories.PokemonRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class FavoritePokemonTest {
    private val mockPokemonRepository: PokemonRepository = mockk()
    private val useCase = FavoritePokemon(pokemonRepository = mockPokemonRepository)

    private val mockPokemon: Pokemon = mockk()

    @BeforeEach
    fun setUp() = clearAllMocks()

    @Test
    fun `use case should call repository exactly once`() {
        // ----- Arrange -----
        coEvery { mockPokemonRepository.favoritePokemon(any()) }.just(runs)

        // ----- Act -----
        runBlocking { useCase(mockPokemon) }

        // ----- Assert -----
        coVerify(exactly = 1) { mockPokemonRepository.favoritePokemon(mockPokemon) }
    }
}