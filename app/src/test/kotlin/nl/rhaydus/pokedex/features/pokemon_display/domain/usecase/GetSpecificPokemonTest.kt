package nl.rhaydus.pokedex.features.pokemon_display.domain.usecase

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import nl.rhaydus.pokedex.core.domain.model.Pokemon
import nl.rhaydus.pokedex.features.pokemon_display.domain.repositories.PokemonRepository
import org.junit.jupiter.api.Test

class GetSpecificPokemonTest {
    private val mockPokemonRepository: PokemonRepository = mockk()
    private val useCase = GetSpecificPokemon(pokemonRepository = mockPokemonRepository)

    @Test
    fun `use case should call repository exactly once`() {
        // ----- Arrange -----
        val mockPokemon: Pokemon = mockk()
        coEvery { mockPokemonRepository.getSpecificPokemon(any()) }.returns(mockPokemon)

        val id = 272

        // ----- Act -----
        runBlocking { useCase(id = id) }

        // ----- Assert -----
        coVerify(exactly = 1) { mockPokemonRepository.getSpecificPokemon(id = id) }
    }
}