package nl.rhaydus.pokedex.features.splash.domain.usecase

import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import kotlinx.coroutines.runBlocking
import nl.rhaydus.pokedex.features.splash.domain.repository.SplashScreenRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class InitializeAllPokemonUseCase {
    private val mockSplashScreenRepository: SplashScreenRepository = mockk()
    private val useCase = InitializeAllPokemon(splashScreenRepository = mockSplashScreenRepository)

    @BeforeEach
    fun setup() {
        clearAllMocks()
    }

    @Test
    fun `use case should call repository exactly once`() {
        // ----- Arrange -----
        coEvery { mockSplashScreenRepository.initializeAllPokemon() }.just(runs)

        // ----- Act -----
        runBlocking { useCase() }

        // ----- Assert -----
        coVerify(exactly = 1) { mockSplashScreenRepository.initializeAllPokemon() }
    }
}