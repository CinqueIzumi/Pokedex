package nl.rhaydus.pokedex.features.pokemon_display.data.data_sources

import android.content.Context
import androidx.sqlite.db.SimpleSQLiteQuery
import io.kotest.matchers.shouldBe
import io.mockk.*
import kotlinx.coroutines.runBlocking
import nl.rhaydus.pokedex.features.pokemon_display.data.dao.PokemonDao
import nl.rhaydus.pokedex.features.pokemon_display.domain.model.Pokemon
import nl.rhaydus.pokedex.fixtures.pokemon
import nl.rhaydus.pokedex.fixtures.pokemonEntity
import nl.rhaydus.pokedex.fixtures.pokemonEntityFavorite
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class LocalPokemonDataSourceImplTest {
    private val mockContext: Context = mockk()
    private val mockPokemonDao: PokemonDao = mockk()

    private val localPokemonDataSource: LocalPokemonDataSource =
        LocalPokemonDataSourceImpl(mockPokemonDao, mockContext)

    @BeforeEach
    fun init() {
        clearAllMocks()
        coEvery {
            mockPokemonDao.getPokemonById(any())
        }.returns(pokemonEntity)
    }

    @Nested
    inner class GetPokemonWithFilter {
        @Nested
        inner class SingleArgument {
            @Test
            fun `should return pokemon based on name`() {
                // ----- Arrange -----
                val queryString = "SELECT * FROM pokemonentity WHERE poke_name LIKE ?;"
                val args: List<Any> = listOf("%bulbasaur%")

                val slot = slot<SimpleSQLiteQuery>()

                coEvery {
                    // Capture the argument which was used within the call
                    mockPokemonDao.getFilteredPokemons(capture(slot))
                }.returns(listOf(pokemonEntity))

                // ----- Act -----
                val result = runBlocking {
                    localPokemonDataSource.getPokemonWithFilter(
                        "bulbasaur",
                        null,
                        null,
                        null
                    )
                }

                // ----- Assert -----
                result shouldBe listOf(pokemon)

                // Verify that the DAO was called with any string, to ensure the data was received from the DAO
                coVerify { mockPokemonDao.getFilteredPokemons(any()) }

                // Note that the following lines are required, as SimpleSQLiteQuery can´t be compared
                slot.captured.sql shouldBe queryString
                slot.captured.argCount shouldBe args.size
            }

            @Test
            fun `should return pokemon based on id`() {
                // ----- Arrange -----
                val queryString = "SELECT * FROM pokemonentity WHERE id = ?;"
                val args: List<Any> = listOf("20")

                val slot = slot<SimpleSQLiteQuery>()

                coEvery {
                    // Capture the argument which was used within the call
                    mockPokemonDao.getFilteredPokemons(capture(slot))
                }.returns(listOf(pokemonEntity))

                // ----- Act -----
                val result = runBlocking {
                    localPokemonDataSource.getPokemonWithFilter(
                        "20",
                        null,
                        null,
                        null
                    )
                }

                // ----- Assert -----
                result shouldBe listOf(pokemon)

                // Verify that the DAO was called with any string, to ensure the data was received from the DAO
                coVerify { mockPokemonDao.getFilteredPokemons(any()) }

                // Note that the following lines are required, as SimpleSQLiteQuery can´t be compared
                slot.captured.sql shouldBe queryString
                slot.captured.argCount shouldBe args.size
            }

            @Test
            fun `should return pokemons based on favorites`() {
                // ----- Arrange -----
                val queryString = "SELECT * FROM pokemonentity WHERE favorite = ?;"
                val args: List<Any> = listOf(true)

                val slot = slot<SimpleSQLiteQuery>()

                coEvery {
                    // Capture the argument which was used within the call
                    mockPokemonDao.getFilteredPokemons(capture(slot))
                }.returns(listOf(pokemonEntity))

                // ----- Act -----
                val result = runBlocking {
                    localPokemonDataSource.getPokemonWithFilter(
                        null,
                        true,
                        null,
                        null
                    )
                }

                // ----- Assert -----
                result shouldBe listOf(pokemon)

                // Verify that the DAO was called with any string, to ensure the data was received from the DAO
                coVerify { mockPokemonDao.getFilteredPokemons(any()) }

                // Note that the following lines are required, as SimpleSQLiteQuery can´t be compared
                slot.captured.sql shouldBe queryString
                slot.captured.argCount shouldBe args.size
            }

            @Test
            fun `should return pokemons based on main types`() {
                // ----- Arrange -----
                val queryString = "SELECT * FROM pokemonentity WHERE poke_main_type = ?;"
                val args: List<Any> = listOf("poison")

                val slot = slot<SimpleSQLiteQuery>()

                coEvery {
                    // Capture the argument which was used within the call
                    mockPokemonDao.getFilteredPokemons(capture(slot))
                }.returns(listOf(pokemonEntity))

                // ----- Act -----
                val result = runBlocking {
                    localPokemonDataSource.getPokemonWithFilter(
                        null,
                        null,
                        "poison",
                        null
                    )
                }

                // ----- Assert -----
                result shouldBe listOf(pokemon)

                // Verify that the DAO was called with any string, to ensure the data was received from the DAO
                coVerify { mockPokemonDao.getFilteredPokemons(any()) }

                // Note that the following lines are required, as SimpleSQLiteQuery can´t be compared
                slot.captured.sql shouldBe queryString
                slot.captured.argCount shouldBe args.size
            }

            @Test
            fun `should ignore main type if main type was all`() {
                // ----- Arrange -----
                val queryString = "SELECT * FROM pokemonentity;"

                val slot = slot<SimpleSQLiteQuery>()

                coEvery {
                    // Capture the argument which was used within the call
                    mockPokemonDao.getFilteredPokemons(capture(slot))
                }.returns(listOf(pokemonEntity))

                // ----- Act -----
                val result = runBlocking {
                    localPokemonDataSource.getPokemonWithFilter(
                        null,
                        null,
                        "All",
                        null
                    )
                }

                // ----- Assert -----
                result shouldBe listOf(pokemon)

                // Verify that the DAO was called with any string, to ensure the data was received from the DAO
                coVerify { mockPokemonDao.getFilteredPokemons(any()) }

                // Note that the following lines are required, as SimpleSQLiteQuery can´t be compared
                slot.captured.sql shouldBe queryString
                slot.captured.argCount shouldBe 0
            }

            @Test
            fun `should return pokemons based on secondary types`() {
                // ----- Arrange -----
                val queryString = "SELECT * FROM pokemonentity WHERE poke_secondary_type = ?;"
                val args: List<Any> = listOf("poison")

                val slot = slot<SimpleSQLiteQuery>()

                coEvery {
                    // Capture the argument which was used within the call
                    mockPokemonDao.getFilteredPokemons(capture(slot))
                }.returns(listOf(pokemonEntity))

                // ----- Act -----
                val result = runBlocking {
                    localPokemonDataSource.getPokemonWithFilter(
                        null,
                        null,
                        null,
                        "Poison"
                    )
                }

                // ----- Assert -----
                result shouldBe listOf(pokemon)

                // Verify that the DAO was called with any string, to ensure the data was received from the DAO
                coVerify { mockPokemonDao.getFilteredPokemons(any()) }

                // Note that the following lines are required, as SimpleSQLiteQuery can´t be compared
                slot.captured.sql shouldBe queryString
                slot.captured.argCount shouldBe args.size
            }

            @Test
            fun `should ignore secondary type if secondary type was all`() {
                // ----- Arrange -----
                val queryString = "SELECT * FROM pokemonentity;"

                val slot = slot<SimpleSQLiteQuery>()

                coEvery {
                    // Capture the argument which was used within the call
                    mockPokemonDao.getFilteredPokemons(capture(slot))
                }.returns(listOf(pokemonEntity))

                // ----- Act -----
                val result = runBlocking {
                    localPokemonDataSource.getPokemonWithFilter(
                        null,
                        null,
                        null,
                        "All"
                    )
                }

                // ----- Assert -----
                result shouldBe listOf(pokemon)

                // Verify that the DAO was called with any string, to ensure the data was received from the DAO
                coVerify { mockPokemonDao.getFilteredPokemons(any()) }

                // Note that the following lines are required, as SimpleSQLiteQuery can´t be compared
                slot.captured.sql shouldBe queryString
                slot.captured.argCount shouldBe 0
            }
        }

        @Nested
        inner class MultipleArguments {
            @Test
            fun `should return pokemon based on name and being favorite`() {
                // ----- Arrange -----
                val queryString =
                    "SELECT * FROM pokemonentity WHERE poke_name LIKE ? AND favorite = ?;"
                val args: List<Any> = listOf("%bulbasaur%", true)

                val slot = slot<SimpleSQLiteQuery>()

                coEvery {
                    // Capture the argument which was used within the call
                    mockPokemonDao.getFilteredPokemons(capture(slot))
                }.returns(listOf(pokemonEntity))

                // ----- Act -----
                val result = runBlocking {
                    localPokemonDataSource.getPokemonWithFilter(
                        "bulbasaur",
                        true,
                        null,
                        null
                    )
                }

                // ----- Assert -----
                result shouldBe listOf(pokemon)

                // Verify that the DAO was called with any string, to ensure the data was received from the DAO
                coVerify { mockPokemonDao.getFilteredPokemons(any()) }

                // Note that the following lines are required, as SimpleSQLiteQuery can´t be compared
                slot.captured.sql shouldBe queryString
                slot.captured.argCount shouldBe args.size
            }

            @Test
            fun `should return pokemon based on name and main type`() {
                // ----- Arrange -----
                val queryString =
                    "SELECT * FROM pokemonentity WHERE poke_name LIKE ? AND poke_main_type = ?;"
                val args: List<Any> = listOf("%bulbasaur%", "Poison")

                val slot = slot<SimpleSQLiteQuery>()

                coEvery {
                    // Capture the argument which was used within the call
                    mockPokemonDao.getFilteredPokemons(capture(slot))
                }.returns(listOf(pokemonEntity))

                // ----- Act -----
                val result = runBlocking {
                    localPokemonDataSource.getPokemonWithFilter(
                        "bulbasaur",
                        null,
                        "Poison",
                        null
                    )
                }

                // ----- Assert -----
                result shouldBe listOf(pokemon)

                // Verify that the DAO was called with any string, to ensure the data was received from the DAO
                coVerify { mockPokemonDao.getFilteredPokemons(any()) }

                // Note that the following lines are required, as SimpleSQLiteQuery can´t be compared
                slot.captured.sql shouldBe queryString
                slot.captured.argCount shouldBe args.size
            }

            @Test
            fun `should return pokemon based on name and secondary type`() {
                // ----- Arrange -----
                val queryString =
                    "SELECT * FROM pokemonentity WHERE poke_name LIKE ? AND poke_secondary_type = ?;"
                val args: List<Any> = listOf("%bulbasaur%", "Poison")

                val slot = slot<SimpleSQLiteQuery>()

                coEvery {
                    // Capture the argument which was used within the call
                    mockPokemonDao.getFilteredPokemons(capture(slot))
                }.returns(listOf(pokemonEntity))

                // ----- Act -----
                val result = runBlocking {
                    localPokemonDataSource.getPokemonWithFilter(
                        "bulbasaur",
                        null,
                        null,
                        "Poison"
                    )
                }

                // ----- Assert -----
                result shouldBe listOf(pokemon)

                // Verify that the DAO was called with any string, to ensure the data was received from the DAO
                coVerify { mockPokemonDao.getFilteredPokemons(any()) }

                // Note that the following lines are required, as SimpleSQLiteQuery can´t be compared
                slot.captured.sql shouldBe queryString
                slot.captured.argCount shouldBe args.size
            }
        }
    }

    @Nested
    inner class UnFavoritePokemon {
        @Test
        fun `returns unit if the pokemon has been removed from the favorites`() {
            // ----- Arrange -----
            coEvery {
                mockPokemonDao.updatePokemon(any())
            }.just(runs)

            // ----- Act -----
            val result = runBlocking { localPokemonDataSource.unFavoritePokemon(pokemon) }

            // ----- Assert -----
            result shouldBe Unit
            coVerify { mockPokemonDao.updatePokemon(pokemonEntity) }
        }
    }

    @Nested
    inner class FavoritePokemon {
        @Test
        fun `returns unit if the pokemon has been added to favorites`() {
            // ----- Assert -----
            coEvery {
                mockPokemonDao.updatePokemon(any())
            }.just(runs)

            // ----- Act -----
            val result = runBlocking { localPokemonDataSource.favoritePokemon(pokemon) }

            // ----- Assert -----
            result shouldBe Unit
            coVerify { mockPokemonDao.updatePokemon(pokemonEntityFavorite) }
        }
    }

    @Nested
    inner class GetSpecificPokemon {
        @Test
        fun `returns a pokemon based on id from local database`() {
            // ----- Act -----
            val result = runBlocking { localPokemonDataSource.getSpecificPokemon(1) }

            // ----- Assert -----
            result shouldBe pokemon
            coVerify { mockPokemonDao.getPokemonById(any()) }
        }
    }

    @Nested
    inner class GetRandomPokemon {
        @Test
        fun `returns a random pokemon from local database`() {
            // ----- Arrange -----
            coEvery {
                mockContext.resources.getInteger(any())
            }.returns(905)

            // ----- Act -----
            val result = runBlocking { localPokemonDataSource.getRandomPokemon() }

            // ----- Assert -----
            result shouldBe pokemon
            coVerify { mockPokemonDao.getPokemonById(any()) }
        }
    }

    @Nested
    inner class GetPokemonUntilId {
        @Test
        fun `returns a list until a specific id from local database`() {
            // ----- Arrange -----
            val list = mutableListOf<Pokemon>()
            for (i in 1..5) {
                list.add(pokemon)
            }
            val pokeList = list.toList()

            // ----- Act -----
            val result = runBlocking { localPokemonDataSource.getPokemonUntilId(5) }

            // ----- Assert -----
            result shouldBe pokeList
            coVerify { mockPokemonDao.getPokemonById(any()) }
        }
    }

    @Nested
    inner class GetAllPokemon {
        @Test
        fun `returns a list of all pokemon from local database`() {
            // ----- Arrange -----
            coEvery {
                mockContext.resources.getInteger(any())
            }.returns(905)

            val list = mutableListOf<Pokemon>()
            for (i in 1..905) {
                list.add(pokemon)
            }
            val pokeList = list.toList()

            // ----- Act -----
            val result = runBlocking { localPokemonDataSource.getAllPokemon() }

            // ----- Assert -----
            result shouldBe pokeList
            coVerify { mockPokemonDao.getPokemonById(any()) }
        }
    }
}