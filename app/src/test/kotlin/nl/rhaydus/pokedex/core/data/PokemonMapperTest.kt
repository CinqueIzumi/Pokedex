package nl.rhaydus.pokedex.core.data

import io.kotest.matchers.shouldBe
import nl.rhaydus.pokedex.core.data.mapper.toPokemon
import nl.rhaydus.pokedex.core.data.mapper.toPokemonEntity
import nl.rhaydus.pokedex.core.domain.model.Pokemon
import nl.rhaydus.pokedex.features.pokemon_display.data.model.PokemonEntity
import nl.rhaydus.pokedex.features.pokemon_display.domain.enums.PokemonTypeEnum
import org.junit.jupiter.api.Test

class PokemonMapperTest {
    private val pokemonId = 1
    private val pokemonName = "Bulbasaur"
    private val pokemonMainType = PokemonTypeEnum.GRASS
    private val pokemonSecondaryType = PokemonTypeEnum.POISON
    private val pokemonArtworkUrl = "https://path/to/image"
    private val pokemonWeight = "6.9 kg"
    private val pokemonHeight = "0.7 m"
    private val pokemonAbilities = "One, Two, Three"
    private val pokemonDescription = "This is the pokemon's description"
    private val pokemonMalePercentage = 50.0

    private val pokemon = Pokemon(
        id = pokemonId,
        name = pokemonName,
        favorite = false,
        mainType = pokemonMainType,
        secondaryType = pokemonSecondaryType,
        artworkUrl = pokemonArtworkUrl,
        weight = pokemonWeight,
        height = pokemonHeight,
        abilities = pokemonAbilities,
        description = pokemonDescription,
        malePercentage = pokemonMalePercentage
    )

    private val pokemonEntity = PokemonEntity(
        id = pokemonId,
        name = pokemonName,
        mainType = pokemonMainType,
        secondaryType = pokemonSecondaryType,
        artworkUrl = pokemonArtworkUrl,
        weight = pokemonWeight,
        height = pokemonHeight,
        abilities = pokemonAbilities,
        description = pokemonDescription,
        malePercentage = pokemonMalePercentage,
        favorite = false
    )

    @Test
    fun `pokemon should be mapped to entity successfully`() {
        // ----- Act -----
        val result = pokemon.toPokemonEntity()

        // ----- Assert -----
        result shouldBe pokemonEntity
    }

    @Test
    fun `pokemon entity should be mapped to pokemon successfully`() {
        // ----- Act -----
        val result = pokemonEntity.toPokemon()

        // ----- Assert -----
        result shouldBe pokemon
    }
}