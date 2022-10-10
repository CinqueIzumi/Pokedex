package nl.rhaydus.pokedex.features.pokemon_display.data.mapper

import io.kotest.matchers.shouldBe
import io.mockk.clearAllMocks
import nl.rhaydus.pokedex.features.pokemon_display.data.model.PokemonEntity
import nl.rhaydus.pokedex.features.pokemon_display.domain.model.Pokemon
import nl.rhaydus.pokedex.fixtures.pokemon
import nl.rhaydus.pokedex.fixtures.pokemonApiModel
import nl.rhaydus.pokedex.fixtures.pokemonEntity
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ModelMapperTest {
    @BeforeEach
    fun init() {
        clearAllMocks()
    }

    @Test
    fun `returns valid pokemon model list from pokemon entity list`() {
        // ----- Arrange -----
        val pokeEntityList: List<PokemonEntity> =
            listOf(pokemonEntity, pokemonEntity, pokemonEntity, pokemonEntity)
        val pokeList: List<Pokemon> = listOf(pokemon, pokemon, pokemon, pokemon)

        // ----- Act -----
        val result = pokeEntityList.toPokemonList()

        // ----- Assert -----
        result shouldBe pokeList
    }

    @Test
    fun `returns valid pokemon model from pokemon api model`() {
        // ----- Act -----
        val result = pokemonApiModel.toPokemon()

        // ----- Assert -----
        result shouldBe pokemon
    }

    @Test
    fun `returns valid pokemon entity from pokemon model`() {
        // ----- Act -----
        val result = pokemon.toPokemonEntity()

        // ----- Assert -----
        result shouldBe pokemonEntity
    }

    @Test
    fun `returns valid pokemon model from pokemon entity`() {
        // ----- Act -----
        val result = pokemonEntity.toPokemon()

        // ----- Assert -----
        result shouldBe pokemon
    }
}