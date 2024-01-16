package nl.rhaydus.pokedex.features.splash.data.datasource

import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.runs
import io.mockk.verify
import kotlinx.coroutines.runBlocking
import nl.rhaydus.pokedex.core.data.constant.HIGHEST_POKEMON_ID
import nl.rhaydus.pokedex.core.data.mapper.toPokemonEntity
import nl.rhaydus.pokedex.core.domain.model.Pokemon
import nl.rhaydus.pokedex.features.pokemon_display.data.dao.PokemonDao
import nl.rhaydus.pokedex.features.pokemon_display.data.model.PokemonEntity
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class LocalSplashScreenDataSourceImplTest {
    private val mockDao: PokemonDao = mockk()
    private val localSplashScreenDataSourceImpl =
        LocalSplashScreenDataSourceImpl(pokemonDao = mockDao)

    @BeforeEach
    fun setup() {
        mockkStatic("nl.rhaydus.pokedex.core.data.mapper.PokemonMapperKt")
    }

    @Nested
    inner class AddPokemon {
        @Test
        fun `each pokemon in the given list should be added to the local data source`() {
            // ----- Arrange -----
            val pokemonList: List<Pokemon> = listOf(mockk(), mockk(), mockk())
            val pokemonEntityList: List<PokemonEntity> = listOf(mockk(), mockk(), mockk())

            pokemonList.zip(pokemonEntityList)
                .forEach { every { it.first.toPokemonEntity() }.returns(it.second) }
            every { mockDao.insert(any()) }.just(runs)

            // ----- Act -----
            runBlocking { localSplashScreenDataSourceImpl.addPokemon(pokemon = pokemonList) }

            // ----- Assert -----
            pokemonEntityList.forEach { verify(exactly = 1) { mockDao.insert(it) } }
            verify(exactly = pokemonList.size) { mockDao.insert(any()) }
        }
    }

    @Nested
    inner class IsLocalDataSourceComplete {
        private fun setUpDatabaseSize(size: Int) {
            every { mockDao.getDatabaseSize() }.returns(size)
        }

        @Test
        fun `determines based on the database size`() {
            // ----- Arrange -----
            setUpDatabaseSize(100)

            // ----- Act -----
            runBlocking { localSplashScreenDataSourceImpl.isLocalDataSourceComplete() }

            // ----- Assert -----
            verify(exactly = 1) { mockDao.getDatabaseSize() }
        }

        @ParameterizedTest
        @CsvSource(value = ["1:false", "${HIGHEST_POKEMON_ID}:true"], delimiter = ':')
        fun `returns true or false depending on size compared to pre-defined HIGHES_POKEMON_ID`(
            size: Int,
            expectedResult: Boolean
        ) {
            // ----- Arrange -----
            setUpDatabaseSize(size)

            // ----- Act -----
            val result = runBlocking { localSplashScreenDataSourceImpl.isLocalDataSourceComplete() }

            // ----- Assert -----
            result shouldBe expectedResult
        }
    }
}