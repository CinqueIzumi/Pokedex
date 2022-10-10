package nl.rhaydus.pokedex.features.pokemon_display.data.data_sources

import android.content.Context
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
    inner class FavoritePokemon {
        @Test
        fun `returns true if the pokemon has been added to favorites`() {
            // ----- Assert -----
            coEvery {
                mockPokemonDao.updatePokemon(any())
            }.just(runs)

            // ----- Act -----
            val result = runBlocking { localPokemonDataSource.favoritePokemon(pokemon) }

            // ----- Assert -----
            result shouldBe true
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