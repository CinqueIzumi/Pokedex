package nl.rhaydus.pokedex.features.pokemon_display.data.mapper

import io.kotest.matchers.shouldBe
import io.mockk.clearAllMocks
import nl.rhaydus.pokedex.features.pokemon_display.data.model.*
import nl.rhaydus.pokedex.features.pokemon_display.domain.model.Pokemon
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ModelMapperTest {
    private val pokemonName: String = "pokemon"
    private val pokemonNameCapitalized: String = "Pokemon"

    private val pokemonId: Int = 20
    private val pokemonImageUrl: String = "url"
    private val pokemonStat: Int = 20

    private val pokemonFirstType: String = "poison"
    private val pokemonSecondaryType: String = "ghost"
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

    private val pokemonApiModel: PokemonApiModel = PokemonApiModel(
        name = pokemonName,
        id = pokemonId,
        sprites = PokemonSpritesApiModel(
            PokemonSpriteOtherApiModel(
                PokemonSpriteOfficialArtApiModel(
                    pokemonImageUrl
                )
            )
        ),
        types = listOf(
            PokemonTypeEntryApiModel(0, PokemonTypeApiModel(pokemonFirstType)),
            PokemonTypeEntryApiModel(1, PokemonTypeApiModel(pokemonSecondaryType))
        ),
        weight = pokemonWeightRaw,
        height = pokemonHeightRaw,
        stats = listOf(
            PokemonStatsEntryApiModel(pokemonStat),
            PokemonStatsEntryApiModel(pokemonStat),
            PokemonStatsEntryApiModel(pokemonStat),
            PokemonStatsEntryApiModel(pokemonStat),
            PokemonStatsEntryApiModel(pokemonStat),
            PokemonStatsEntryApiModel(pokemonStat)
        )
    )

    @BeforeEach
    fun init() {
        clearAllMocks()
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