package nl.rhaydus.pokedex.features.pokemon_display.data.data_sources

import android.content.Context
import io.kotest.matchers.shouldBe
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import nl.rhaydus.pokedex.core.EmptyPokemonBody
import nl.rhaydus.pokedex.features.pokemon_display.data.network.PokemonApiService
import nl.rhaydus.pokedex.features.pokemon_display.domain.model.Pokemon
import nl.rhaydus.pokedex.fixtures.pokemon
import nl.rhaydus.pokedex.fixtures.pokemonApiModel
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import retrofit2.Response

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class RemotePokemonDataSourceImplTest {
    private val mockPokemonApiService: PokemonApiService = mockk()
    private val mockContext: Context = mockk(relaxed = true)

    private val remotePokemonDataSource: RemotePokemonDataSource =
        RemotePokemonDataSourceImpl(mockPokemonApiService, mockContext)
    private val emptyPokemonBodyException: EmptyPokemonBody = EmptyPokemonBody()

    @BeforeEach
    fun init() {
        clearAllMocks()
    }

    @Nested
    inner class GetAllPokemon {
        @Test
        fun `returns a list of pokemon if call was successful`() {
            // ----- Arrange -----
            coEvery {
                mockPokemonApiService.getSpecificPokemon(any())
            }.returns(Response.success(pokemonApiModel))

            coEvery {
                mockContext.resources.getInteger(any())
            }.returns(905)

            val pokeList = mutableListOf<Pokemon>()
            for (i in 1..905) {
                pokeList.add(pokemon)
            }
            val pokeListFinal = pokeList.toList()

            // ----- Act -----
            val result = runBlocking { remotePokemonDataSource.getAllPokemon() }

            // ----- Assert -----
            result shouldBe pokeListFinal
            coVerify { mockPokemonApiService.getSpecificPokemon(any()) }
            coVerify { mockContext.resources.getInteger(any()) }
        }

        @Test
        fun `throws an EmptyPokemonBody if a pokemon could not be found`() {
            // ----- Arrange -----
            coEvery {
                mockPokemonApiService.getSpecificPokemon(any())
            }.throws(emptyPokemonBodyException)

            coEvery {
                mockContext.resources.getInteger(any())
            }.returns(905)

            // ----- Act -----
            var exception: Exception? = null

            try {
                runBlocking {
                    remotePokemonDataSource.getAllPokemon()
                }
            } catch (e: Exception) {
                exception = e
            }

            // ----- Assert -----
            exception shouldBe emptyPokemonBodyException
            coVerify { mockPokemonApiService.getSpecificPokemon(any()) }
            coVerify { mockContext.resources.getInteger(any()) }
        }
    }
}