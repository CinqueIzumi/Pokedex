package nl.rhaydus.pokedex.fixtures

import nl.rhaydus.pokedex.features.pokemon_display.data.model.*
import nl.rhaydus.pokedex.features.pokemon_display.domain.model.Pokemon


private const val pokemonId: Int = 20
private const val pokemonImageUrl: String = "url"
private const val pokemonStat: Int = 20

private const val favorite: Int = 0

private const val pokemonName: String = "pokemon"
private const val pokemonNameCapitalized: String = "Pokemon"

private const val pokemonFirstType: String = "poison"
private const val pokemonSecondaryType: String = "ghost"
private const val pokemonFirstTypeCapitalized: String = "Poison"
private const val pokemonSecondaryTypeCapitalized: String = "Ghost"

private const val pokemonWeightRaw: Double = 200.0
private const val pokemonHeightRaw: Double = 200.0
private const val pokemonWeight: Double = pokemonWeightRaw / 10
private const val pokemonHeight: Double = pokemonHeightRaw / 10

val pokemon: Pokemon = Pokemon(
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
    spdStat = pokemonStat,
    favorite = favorite,
)

val pokemonEntity: PokemonEntity = PokemonEntity(
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
    pokeStatSpd = pokemonStat,
    favorite = favorite
)

val pokemonApiModel: PokemonApiModel = PokemonApiModel(
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