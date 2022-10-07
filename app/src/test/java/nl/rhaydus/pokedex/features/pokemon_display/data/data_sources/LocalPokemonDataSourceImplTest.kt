package nl.rhaydus.pokedex.features.pokemon_display.data.data_sources

import android.content.Context
import io.kotest.matchers.shouldBe
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import nl.rhaydus.pokedex.features.pokemon_display.data.dao.PokemonDao
import nl.rhaydus.pokedex.features.pokemon_display.data.model.*
import nl.rhaydus.pokedex.features.pokemon_display.domain.model.Pokemon
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class LocalPokemonDataSourceImplTest {
    private val mockContext: Context = mockk()
    private val mockPokemonDao: PokemonDao = mockk()


    private val pokemonId: Int = 20
    private val pokemonImageUrl: String = "url"
    private val pokemonStat: Int = 20

    private val pokemonNameCapitalized: String = "Pokemon"
    private val pokemonFirstTypeCapitalized: String = "Poison"
    private val pokemonSecondaryTypeCapitalized: String = "Ghost"

    private val pokemonWeightRaw: Double = 200.0
    private val pokemonHeightRaw: Double = 200.0
    private val pokemonWeight: Double = pokemonWeightRaw / 10
    private val pokemonHeight: Double = pokemonHeightRaw / 10

    private val pokemon: Pokemon = Pokemon(
        name = pokemonNameCapitalized,
        id = pokemonId,
        imageUrl = pokemonImageUrl,
        types = listOf(pokemonFirstTypeCapitalized, pokemonSecondaryTypeCapitalized),
        weight = pokemonWeight,
        height = pokemonHeight,
        hpStat = pokemonStat,
        atkStat = pokemonStat,
        defStat = pokemonStat,
        spAtkStat = pokemonStat,
        spDefStat = pokemonStat,
        spdStat = pokemonStat
    )

    private val pokemonEntity: PokemonEntity = PokemonEntity(
        id = pokemonId,
        pokeName = pokemonNameCapitalized,
        pokeImageUrl = pokemonImageUrl,
        pokeMainType = pokemonFirstTypeCapitalized,
        pokeSecondaryType = pokemonSecondaryTypeCapitalized,
        pokeWeight = pokemonWeight,
        pokeHeight = pokemonHeight,
        pokeStatHp = pokemonStat,
        pokeStatAtk = pokemonStat,
        pokeStatDef = pokemonStat,
        pokeStatSpAtk = pokemonStat,
        pokeStatSpDef = pokemonStat,
        pokeStatSpd = pokemonStat
    )

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