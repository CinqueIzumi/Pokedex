package nl.rhaydus.pokedex.core.data.constant

import nl.rhaydus.pokedex.core.domain.model.Pokemon
import nl.rhaydus.pokedex.features.pokemon_display.domain.enums.PokemonTypeEnum

// For testing purposes, the pokemon are currently limited to the first generation
const val HIGHEST_POKEMON_ID = 151
//const val HIGHEST_POKEMON_ID = 1010


// This object is purely for previewing composables
val PREVIEW_POKEMON = Pokemon(
    id = 1,
    name = "Bulbasaur",
    mainType = PokemonTypeEnum.GRASS,
    secondaryType = PokemonTypeEnum.POISON,
    artworkUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/1.png",
    weight = "6.9 kg",
    height = "0.7 m",
    abilities = "One, Two, Three",
    description = "For some time after its birth, it grows by taking nourishment from the seed on its back. ",
    malePercentage = 50.0,
    favorite = false
)