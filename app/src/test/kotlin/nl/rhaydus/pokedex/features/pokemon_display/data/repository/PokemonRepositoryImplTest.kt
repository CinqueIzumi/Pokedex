package nl.rhaydus.pokedex.features.pokemon_display.data.repository

import io.kotest.matchers.shouldBe
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import kotlinx.coroutines.runBlocking
import nl.rhaydus.pokedex.core.domain.model.Pokemon
import nl.rhaydus.pokedex.features.pokemon_display.data.datasource.LocalPokemonDataSource
import nl.rhaydus.pokedex.features.pokemon_display.data.datasource.RemotePokemonDataSource
import nl.rhaydus.pokedex.features.pokemon_display.data.repositories.PokemonRepositoryImpl
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class PokemonRepositoryImplTest {
    private val mockRemotePokemonDataSource: RemotePokemonDataSource = mockk()
    private val mockLocalPokemonDataSource: LocalPokemonDataSource = mockk()
    private val mockPokemon: Pokemon = mockk()

    private val pokemonRepositoryImpl = PokemonRepositoryImpl(
        remotePokemonDataSource = mockRemotePokemonDataSource,
        localPokemonDataSource = mockLocalPokemonDataSource
    )

    @BeforeEach
    fun setup() {
        clearAllMocks()
    }

    @Nested
    inner class GetSpecificPokemon {
        private fun setUpSuccessfulGetSpecificPokemonCall() {
            coEvery { mockRemotePokemonDataSource.getSpecificPokemon(any()) }.returns(mockPokemon)
            coEvery { mockLocalPokemonDataSource.addPokemon(any()) }.just(runs)
        }

        @Test
        fun `should call remote data source for pokemon's information`() {
            // ----- Arrange -----
            val idToSearch = 272
            setUpSuccessfulGetSpecificPokemonCall()

            // ----- Act -----
            runBlocking { pokemonRepositoryImpl.getSpecificPokemon(idToSearch) }

            // ----- Assert -----
            coVerify(exactly = 1) { mockRemotePokemonDataSource.getSpecificPokemon(idToSearch) }
        }

        @Test
        fun `should call local data source to save retrieved pokemon locally`() {
            // ----- Arrange -----
            setUpSuccessfulGetSpecificPokemonCall()

            // ----- Act -----
            runBlocking { pokemonRepositoryImpl.getSpecificPokemon(id = 272) }

            // ----- Assert -----
            coVerify(exactly = 1) { mockLocalPokemonDataSource.addPokemon(mockPokemon) }
        }

        @Test
        fun `should return retrieved pokemon`() {
            // ----- Arrange -----
            setUpSuccessfulGetSpecificPokemonCall()

            // ----- Act -----
            val result = runBlocking { pokemonRepositoryImpl.getSpecificPokemon(id = 272) }

            // ----- Assert -----
            result shouldBe mockPokemon
        }
    }

    @Nested
    inner class GetAllPokemon {
        private val expectedList = listOf(mockPokemon, mockPokemon, mockPokemon)

        private fun setUpSuccessfulGetAllPokemonCall() {
            coEvery { mockLocalPokemonDataSource.getAllPokemon() }.returns(expectedList)
        }

        @Test
        fun `should call local data source to get all pokemon`() {
            // ----- Arrange -----
            setUpSuccessfulGetAllPokemonCall()

            // ----- Act -----
            runBlocking { pokemonRepositoryImpl.getAllPokemon() }

            // ----- Assert -----
            coVerify(exactly = 1) { mockLocalPokemonDataSource.getAllPokemon() }
        }

        @Test
        fun `should return retrieved list of pokemon`() {
            // ----- Arrange -----
            setUpSuccessfulGetAllPokemonCall()

            // ----- Act -----
            val result = runBlocking { pokemonRepositoryImpl.getAllPokemon() }

            // ----- Assert -----
            result shouldBe expectedList
        }
    }

    @Nested
    inner class GetPokemonWithFilter {
        private val expectedList = listOf(mockPokemon, mockPokemon, mockPokemon)

        private fun setUpSuccessfulGetPokemonWithFilterCall() {
            coEvery { mockLocalPokemonDataSource.getPokemonWithFilter(any(), any(), any()) }
                .returns(expectedList)
        }

        @Test
        fun `should call local data source to get pokemon which match the given filter`() {
            // ----- Arrange -----
            val name = "ludicolo"
            val isFavorite = true
            val mainType = null
            setUpSuccessfulGetPokemonWithFilterCall()

            // ----- Act -----
            runBlocking {
                pokemonRepositoryImpl.getPokemonWithFilter(
                    nameOrId = name,
                    isFavorite = isFavorite,
                    mainType = mainType
                )
            }

            // ----- Assert -----
            coVerify {
                mockLocalPokemonDataSource.getPokemonWithFilter(
                    nameOrId = name,
                    isFavorite = isFavorite,
                    mainType = mainType
                )
            }
        }

        @Test
        fun `should return retrieved list of pokemon`() {
            // ----- Arrange -----
            val name = "ludicolo"
            val isFavorite = null
            val mainType = null
            setUpSuccessfulGetPokemonWithFilterCall()

            // ----- Act -----
            val result = runBlocking {
                pokemonRepositoryImpl.getPokemonWithFilter(
                    nameOrId = name,
                    isFavorite = isFavorite,
                    mainType = mainType
                )
            }

            // ----- Assert -----
            result shouldBe expectedList
        }
    }
}