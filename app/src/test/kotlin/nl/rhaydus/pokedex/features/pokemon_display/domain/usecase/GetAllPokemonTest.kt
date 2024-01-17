package nl.rhaydus.pokedex.features.pokemon_display.domain.usecase

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import nl.rhaydus.pokedex.features.pokemon_display.domain.repositories.PokemonRepository
import org.junit.jupiter.api.Test

class GetAllPokemonTest {
    private val mockPokemonRepository: PokemonRepository = mockk()
    private val useCase = GetAllPokemon(pokemonRepository = mockPokemonRepository)

    @Test
    fun `use case should call repository exactly once`() {
        // ----- Arrange -----
        coEvery { mockPokemonRepository.getAllPokemon() }.returns(emptyList())

        // ----- Act -----
        runBlocking { useCase() }

        // ----- Assert -----
        coVerify(exactly = 1) { mockPokemonRepository.getAllPokemon() }
    }
}