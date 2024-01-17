package nl.rhaydus.pokedex.features.pokemon_display.domain.usecase

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import nl.rhaydus.pokedex.features.pokemon_display.domain.repositories.PokemonRepository
import org.junit.jupiter.api.Test

class GetPokemonWithFilterTest {
    private val mockPokemonRepository: PokemonRepository = mockk()
    private val useCase = GetPokemonWithFilter(pokemonRepository = mockPokemonRepository)

    @Test
    fun `use case should call repository exactly once`() {
        // ----- Arrange -----
        coEvery { mockPokemonRepository.getPokemonWithFilter(any(), any(), any()) }
            .returns(emptyList())

        val nameOrId = "Ludicolo"
        val isFavorite = false
        val mainType: String? = null

        // ----- Act -----
        runBlocking { useCase(nameOrId = nameOrId, isFavorite = isFavorite, mainType = mainType) }

        // ----- Assert -----
        coVerify(exactly = 1) {
            mockPokemonRepository.getPokemonWithFilter(
                nameOrId = nameOrId,
                isFavorite = isFavorite,
                mainType = mainType
            )
        }
    }
}