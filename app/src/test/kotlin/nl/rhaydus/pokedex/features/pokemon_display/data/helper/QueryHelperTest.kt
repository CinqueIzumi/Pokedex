package nl.rhaydus.pokedex.features.pokemon_display.data.helper

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class QueryHelperTest {
    private val queryBase = "SELECT * FROM pokemonentity"

    @Test
    fun `query supports name`() {
        // ----- Arrange -----
        val pokemonName = "bulbasaur"
        val expectedQuery = "$queryBase WHERE poke_name LIKE ?;"

        // ----- Act -----
        val result =
            QueryHelper.getQuery(nameOrId = pokemonName, isFavorite = null, mainType = null)

        // ----- Assert -----
        result.sql shouldBe expectedQuery
    }

    @Test
    fun `query supports id`() {
        // ----- Arrange -----
        val pokemonId = 20
        val expectedQuery = "$queryBase WHERE id = ?;"

        // ----- Act -----
        val result = QueryHelper.getQuery(
            nameOrId = pokemonId.toString(),
            isFavorite = null,
            mainType = null
        )

        // ----- Assert -----
        result.sql shouldBe expectedQuery
    }

    @Test
    fun `query supports favorites`() {
        // ----- Arrange -----
        val isFavorite = true
        val expectedQuery = "$queryBase WHERE favorite = ?;"

        // ----- Act -----
        val result = QueryHelper.getQuery(
            nameOrId = null,
            isFavorite = isFavorite,
            mainType = null
        )

        // ----- Assert -----
        result.sql shouldBe expectedQuery
    }

    @Test
    fun `query supports main type`() {
        // ----- Arrange -----
        val mainType = "water"
        val expectedQuery = "$queryBase WHERE poke_main_type = ?;"

        // ----- Act -----
        val result = QueryHelper.getQuery(nameOrId = null, isFavorite = null, mainType = mainType)

        // ----- Assert -----
        result.sql shouldBe expectedQuery
    }

    @Nested
    inner class MultipleQuery {
        @Test
        fun `name and fav`() {
            // ----- Arrange -----
            val name = "bulbasaur"
            val favorite = true

            val expectedQuery = "$queryBase WHERE poke_name LIKE ? AND favorite = ?;"

            // ----- Act -----
            val result =
                QueryHelper.getQuery(nameOrId = name, isFavorite = favorite, mainType = null)

            // ----- Assert -----
            result.sql shouldBe expectedQuery
        }

        @Test
        fun `fav and main type`() {
            // ----- Arrange -----
            val favorite = false
            val mainType = "water"

            val expectedQuery = "$queryBase WHERE favorite = ? AND poke_main_type = ?;"

            // ----- Act -----
            val result =
                QueryHelper.getQuery(nameOrId = null, isFavorite = favorite, mainType = mainType)

            // ----- Assert -----
            result.sql shouldBe expectedQuery
        }

        @Test
        fun `name, fav and main type`() {
            // ----- Arrange -----
            val name = "bulbasaur"
            val favorite = true
            val mainType = "water"

            val expectedQuery =
                "$queryBase WHERE poke_name LIKE ? AND favorite = ? AND poke_main_type = ?;"

            // ----- Act -----
            val result =
                QueryHelper.getQuery(nameOrId = name, isFavorite = favorite, mainType = mainType)

            // ----- Assert -----
            result.sql shouldBe expectedQuery
        }
    }
}