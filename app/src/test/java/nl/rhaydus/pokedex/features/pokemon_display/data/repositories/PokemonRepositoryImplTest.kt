package nl.rhaydus.pokedex.features.pokemon_display.data.repositories

import io.kotest.matchers.shouldBe
import io.mockk.*
import kotlinx.coroutines.runBlocking
import nl.rhaydus.pokedex.core.LocalDataError
import nl.rhaydus.pokedex.features.pokemon_display.data.data_sources.LocalPokemonDataSource
import nl.rhaydus.pokedex.features.pokemon_display.data.data_sources.RemotePokemonDataSource
import nl.rhaydus.pokedex.features.pokemon_display.domain.model.Pokemon
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PokemonRepositoryImplTest {
    private val mockLocalPokemonDataSource: LocalPokemonDataSource = mockk(relaxed = true)
    private val mockRemotePokemonDataSource: RemotePokemonDataSource = mockk(relaxed = true)
    private val mockPokemon: Pokemon = mockk()

    private val pokemonList: List<Pokemon> = listOf(mockPokemon, mockPokemon, mockPokemon)
    private val pokemonRepositoryImpl: PokemonRepositoryImpl =
        PokemonRepositoryImpl(mockRemotePokemonDataSource, mockLocalPokemonDataSource)
    private val exception: Exception = Exception()
    private val localDataError: Exception = LocalDataError()

    @BeforeEach
    fun init() {
        clearAllMocks()
    }

    @Nested
    inner class GetPokemonWithFilter {
        @Test
        fun `returns pokemon from local data source`() {
            // ----- Arrange -----
            coEvery {
                mockLocalPokemonDataSource.getPokemonWithFilter(any(), any(), any(), any())
            }.returns(pokemonList)

            // ----- Act -----
            val result = runBlocking {
                pokemonRepositoryImpl.getPokemonWithFilter(null, null, null, null)
            }

            // ----- Assert -----
            result shouldBe Result.success(pokemonList)
            coVerify { mockLocalPokemonDataSource.getPokemonWithFilter(any(), any(), any(), any()) }
            coVerify { mockRemotePokemonDataSource wasNot called }
        }

        @Test
        fun `returns error when pokemons could not be loaded`() {
            // ----- Arrange -----
            coEvery {
                mockLocalPokemonDataSource.getPokemonWithFilter(any(), any(), any(), any())
            }.throws(exception)

            // ----- Act -----
            val result = try {
                runBlocking {
                    pokemonRepositoryImpl.getPokemonWithFilter(null, null, null, null)
                }
            } catch (e: Exception) {
                e
            }

            // ----- Assert -----
            result shouldBe Result.failure<Exception>(exception)
            coVerify { mockLocalPokemonDataSource.getPokemonWithFilter(any(), any(), any(), any()) }
            coVerify { mockRemotePokemonDataSource wasNot called }
        }
    }

    @Nested
    inner class UnFavoritePokemon {
        @Test
        fun `returns true if pokemon was removed from favorites within local data source `() {
            // ----- Arrange -----
            coEvery {
                mockLocalPokemonDataSource.unFavoritePokemon(any())
            }.returns(true)

            // ----- Act -----
            val result = runBlocking { pokemonRepositoryImpl.unFavoritePokemon(mockPokemon) }

            // ----- Assert -----
            result shouldBe Result.success(true)
            coVerify { mockLocalPokemonDataSource.unFavoritePokemon(any()) }
            coVerify { mockRemotePokemonDataSource wasNot called }
        }

        @Test
        fun `returns error if pokemon could not be removed from favorites within local data source`() {
            // ----- Arrange -----
            coEvery {
                mockLocalPokemonDataSource.unFavoritePokemon(any())
            }.throws(localDataError)

            // ----- Act -----
            val result = try {
                runBlocking {
                    pokemonRepositoryImpl.unFavoritePokemon(mockPokemon)
                }
            } catch (e: Exception) {
                e
            }

            // ----- Assert -----
            result shouldBe Result.failure<LocalDataError>(localDataError)
            coVerify { mockLocalPokemonDataSource.unFavoritePokemon(any()) }
            coVerify { mockRemotePokemonDataSource wasNot called }
        }
    }

    @Nested
    inner class FavoritePokemon {
        @Test
        fun `returns true if pokemon was added to favorites within local data source`() {
            // ----- Arrange -----
            coEvery {
                mockLocalPokemonDataSource.favoritePokemon(any())
            }.returns(true)

            // ----- Act -----
            val result = runBlocking { pokemonRepositoryImpl.favoritePokemon(mockPokemon) }

            // ----- Assert -----
            result shouldBe Result.success(true)
            coVerify { mockLocalPokemonDataSource.favoritePokemon(any()) }
            coVerify { mockRemotePokemonDataSource wasNot called }
        }

        @Test
        fun `returns error if pokemon could not be saved to local data source`() {
            // ----- Arrange -----
            coEvery {
                mockLocalPokemonDataSource.favoritePokemon(any())
            }.throws(localDataError)

            // ----- Act -----
            val result = try {
                runBlocking {
                    pokemonRepositoryImpl.favoritePokemon(mockPokemon)
                }
            } catch (e: Exception) {
                e
            }

            // ----- Assert -----
            result shouldBe Result.failure<LocalDataError>(localDataError)
            coVerify { mockLocalPokemonDataSource.favoritePokemon(any()) }
            coVerify { mockRemotePokemonDataSource wasNot called }
        }
    }

    @Nested
    inner class GetPokemonUntilId {
        @Test
        fun `returns pokemons from local data source`() {
            // ----- Arrange -----
            coEvery {
                mockLocalPokemonDataSource.getPokemonUntilId(any())
            }.returns(pokemonList)

            // ----- Act -----
            val result = runBlocking { pokemonRepositoryImpl.getPokemonUntilId(1) }

            // ----- Assert -----
            result shouldBe Result.success(pokemonList)
            coVerify { mockLocalPokemonDataSource.getPokemonUntilId(any()) }
            coVerify { mockRemotePokemonDataSource wasNot called }
        }

        @Test
        fun `returns error when pokemons could not be loaded`() {
            // ----- Arrange -----
            coEvery {
                mockLocalPokemonDataSource.getPokemonUntilId(any())
            }.throws(exception)

            // ----- Act -----
            val result = try {
                runBlocking {
                    pokemonRepositoryImpl.getPokemonUntilId(1)
                }
            } catch (e: Exception) {
                e
            }

            // ----- Assert -----
            result shouldBe Result.failure<Exception>(exception)
            coVerify { mockLocalPokemonDataSource.getPokemonUntilId(any()) }
            coVerify { mockRemotePokemonDataSource wasNot called }
        }
    }

    @Nested
    inner class GetRandomPokemon {
        @Test
        fun `returns pokemon from local data source`() {
            // ----- Arrange -----
            coEvery {
                mockLocalPokemonDataSource.getRandomPokemon()
            }.returns(mockPokemon)

            // ----- Act -----
            val result = runBlocking { pokemonRepositoryImpl.getRandomPokemon() }

            // ----- Assert -----
            result shouldBe Result.success(mockPokemon)
            coVerify { mockLocalPokemonDataSource.getRandomPokemon() }
            coVerify { mockRemotePokemonDataSource wasNot called }
        }

        @Test
        fun `returns error when pokemon could not be loaded`() {
            // ----- Arrange -----
            coEvery {
                mockLocalPokemonDataSource.getRandomPokemon()
            }.throws(exception)

            // ----- Act -----
            val result = try {
                runBlocking {
                    pokemonRepositoryImpl.getRandomPokemon()
                }
            } catch (e: Exception) {
                e
            }

            // ----- Assert -----
            result shouldBe Result.failure<Exception>(exception)
            coVerify { mockLocalPokemonDataSource.getRandomPokemon() }
            coVerify { mockRemotePokemonDataSource wasNot called }
        }
    }

    @Nested
    inner class GetSpecificPokemon {
        @Test
        fun `returns pokemon from local data source`() {
            // ----- Arrange -----
            coEvery {
                mockLocalPokemonDataSource.getSpecificPokemon(any())
            }.returns(mockPokemon)

            // ----- Act -----
            val result = runBlocking { pokemonRepositoryImpl.getSpecificPokemon(1) }

            // ----- Assert -----
            result shouldBe Result.success(mockPokemon)
            coVerify { mockLocalPokemonDataSource.getSpecificPokemon(any()) }
            coVerify { mockRemotePokemonDataSource wasNot called }
        }

        @Test
        fun `returns error when pokemon could not be loaded`() {
            // ----- Arrange -----
            coEvery {
                mockLocalPokemonDataSource.getSpecificPokemon(any())
            }.throws(exception)

            // ----- Act -----
            val result = try {
                runBlocking {
                    pokemonRepositoryImpl.getSpecificPokemon(1)
                }
            } catch (e: Exception) {
                e
            }

            // ----- Assert -----
            result shouldBe Result.failure<Exception>(exception)
            coVerify { mockLocalPokemonDataSource.getSpecificPokemon(any()) }
            coVerify { mockRemotePokemonDataSource wasNot called }
        }
    }

    @Nested
    inner class GetAllPokemon {
        @Test
        fun `returns pokemon from local data source if possible`() {
            // ----- Arrange -----
            coEvery {
                mockLocalPokemonDataSource.getAllPokemon()
            }.returns(pokemonList)

            coEvery {
                mockLocalPokemonDataSource.isLocalDataComplete()
            }.returns(true)

            // ----- Act -----
            val result = runBlocking { pokemonRepositoryImpl.getAllPokemon() }

            // ----- Assert -----
            result shouldBe Result.success(pokemonList)
            coVerify { mockLocalPokemonDataSource.getAllPokemon() }
            coVerify { mockLocalPokemonDataSource.isLocalDataComplete() }
            coVerify(exactly = 0) { mockRemotePokemonDataSource.getAllPokemon() }
        }

        @Test
        fun `loads pokemon from remote data source if local data is incomplete`() {
            // ----- Arrange -----
            coEvery {
                mockLocalPokemonDataSource.getAllPokemon()
            }.returns(pokemonList)

            coEvery {
                mockLocalPokemonDataSource.isLocalDataComplete()
            }.returns(false)

            coEvery {
                mockRemotePokemonDataSource.getAllPokemon()
            }.returns(pokemonList)

            // ----- Act -----
            val result = runBlocking { pokemonRepositoryImpl.getAllPokemon() }

            // ----- Assert -----
            result shouldBe Result.success(pokemonList)
            coVerify(exactly = 1) { mockLocalPokemonDataSource.getAllPokemon() }
            coVerify { mockLocalPokemonDataSource.isLocalDataComplete() }
            coVerify { mockRemotePokemonDataSource.getAllPokemon() }
        }

        @Test
        fun `returns error if local database could not be accessed`() {
            // ----- Arrange -----
            coEvery {
                mockLocalPokemonDataSource.getAllPokemon()
            }.throws(exception)

            coEvery {
                mockLocalPokemonDataSource.isLocalDataComplete()
            }.returns(true)

            // ----- Act -----
            val result = try {
                runBlocking { pokemonRepositoryImpl.getAllPokemon() }
            } catch (e: Exception) {
                e
            }

            // ----- Assert -----
            result shouldBe Result.failure<Exception>(exception)
            coVerify { mockLocalPokemonDataSource.getAllPokemon() }
            coVerify { mockLocalPokemonDataSource.isLocalDataComplete() }
        }

        @Test
        fun `returns error if remote call failed`() {
            // ----- Arrange -----
            coEvery {
                mockLocalPokemonDataSource.getAllPokemon()
            }.returns(pokemonList)

            coEvery {
                mockLocalPokemonDataSource.isLocalDataComplete()
            }.returns(false)

            coEvery {
                mockRemotePokemonDataSource.getAllPokemon()
            }.throws(exception)

            // ----- Act -----
            val result = try {
                runBlocking { pokemonRepositoryImpl.getAllPokemon() }
            } catch (e: Exception) {
                e
            }

            // ----- Assert -----
            result shouldBe Result.failure<Exception>(exception)
            coVerify { mockLocalPokemonDataSource.isLocalDataComplete() }
            coVerify { mockRemotePokemonDataSource.getAllPokemon() }
        }
    }
}