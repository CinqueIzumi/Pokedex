package nl.rhaydus.pokedex.features.splash.data.repository

import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import kotlinx.coroutines.runBlocking
import nl.rhaydus.pokedex.core.domain.model.Pokemon
import nl.rhaydus.pokedex.features.splash.data.datasource.LocalSplashScreenDataSource
import nl.rhaydus.pokedex.features.splash.data.datasource.RemoteSplashScreenDataSource
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class SplashScreenRepositoryImplTest {
    private val mockRemoteDataSource: RemoteSplashScreenDataSource = mockk()
    private val mockLocalDataSource: LocalSplashScreenDataSource = mockk()

    private val pokemonList: List<Pokemon> = emptyList()

    private val repositoryImpl = SplashScreenRepositoryImpl(
        localSplashScreenDataSource = mockLocalDataSource,
        remoteSplashScreenDataSource = mockRemoteDataSource
    )

    @BeforeEach
    fun setup() {
        clearAllMocks()
    }

    private fun setUpIncompleteLocalDataSource() {
        coEvery { mockLocalDataSource.isLocalDataSourceComplete() }.returns(false)
        coEvery { mockRemoteDataSource.initializeAllPokemon() }.returns(pokemonList)
        coEvery { mockLocalDataSource.addPokemon(any()) }.just(runs)
    }

    @Test
    fun `remote data source should not be called if local data source was complete`() {
        // ----- Arrange -----
        coEvery { mockLocalDataSource.isLocalDataSourceComplete() }.returns(true)

        // ----- Act -----
        runBlocking { repositoryImpl.initializeAllPokemon() }

        // ----- Assert -----
        coVerify(exactly = 1) { mockLocalDataSource.isLocalDataSourceComplete() }
        coVerify(exactly = 0) { mockRemoteDataSource.initializeAllPokemon() }
    }

    @Test
    fun `remote data source should be called if local data source was incomplete`() {
        // ----- Arrange -----
        setUpIncompleteLocalDataSource()

        // ----- Act -----
        runBlocking { repositoryImpl.initializeAllPokemon() }

        // ----- Assert -----
        coVerify(exactly = 1) { mockRemoteDataSource.initializeAllPokemon() }
    }

    @Test
    fun `local data source should be appended with retrieved remote data`() {
        // ----- Arrange -----
        setUpIncompleteLocalDataSource()

        // ----- Act -----
        runBlocking { repositoryImpl.initializeAllPokemon() }

        // ----- Assert -----
        coVerify(exactly = 1) { mockLocalDataSource.addPokemon(pokemonList) }
    }
}