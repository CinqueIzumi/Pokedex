package nl.rhaydus.pokedex.features.splash.data.datasource

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import kotlinx.coroutines.runBlocking
import nl.rhaydus.pokedex.core.domain.model.Pokemon
import nl.rhaydus.pokedex.features.splash.data.mapper.toPokemonList
import nl.rhaydus.pokedex.features.splash.data.network.SplashScreenApiService
import nl.rhaydus.pokedex.features.splash.data.network.response.GlobalPokemonResponse
import nl.rhaydus.pokedex.features.splash.domain.exception.SplashScreenException
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import retrofit2.Response

class RemoteSplashScreenDataSourceImplTest {
    private val mockSplashScreenApiService: SplashScreenApiService = mockk()
    private val mockResponse: Response<GlobalPokemonResponse> = mockk()

    private val remoteSplashScreenDataSourceImpl =
        RemoteSplashScreenDataSourceImpl(splashScreenApiService = mockSplashScreenApiService)

    @BeforeEach
    fun setup() {
        clearAllMocks()
        mockkStatic("nl.rhaydus.pokedex.features.splash.data.mapper.PokemonInfoMapperKt")

        setUpApiService()
    }

    private fun setUpApiService() {
        coEvery { mockSplashScreenApiService.getAllPokemonFromApi() }.returns(mockResponse)
    }

    private fun setResponseSuccessful(successful: Boolean) {
        every { mockResponse.isSuccessful }.returns(successful)
    }

    @Test
    fun `initializeAllPokemon throws FailedResponse if response was not successful`() {
        // ----- Arrange -----
        setResponseSuccessful(successful = false)

        // ----- Act -----
        shouldThrow<SplashScreenException.FailedResponse> {
            runBlocking { remoteSplashScreenDataSourceImpl.initializeAllPokemon() }
        }
    }

    @Test
    fun `initializeAllPokemon throws EmptyResponseBody if response body was empty`() {
        // ----- Arrange -----
        setResponseSuccessful(successful = true)
        every { mockResponse.body() }.returns(null)

        // ----- Act -----
        shouldThrow<SplashScreenException.EmptyResponseBody> {
            runBlocking { remoteSplashScreenDataSourceImpl.initializeAllPokemon() }
        }
    }

    @Test
    fun `initializeAllPokemon returns pokemon list if call was successful`() {
        // ----- Arrange -----
        val expectedValue = emptyList<Pokemon>()

        setResponseSuccessful(successful = true)
        val mockGlobalPokemonResponse: GlobalPokemonResponse = mockk()
        every { mockResponse.body() }.returns(mockGlobalPokemonResponse)
        every { mockGlobalPokemonResponse.toPokemonList() }.returns(expectedValue)

        // ----- Act -----
        val result = runBlocking { remoteSplashScreenDataSourceImpl.initializeAllPokemon() }

        // ----- Assert -----
        result shouldBe expectedValue
    }
}