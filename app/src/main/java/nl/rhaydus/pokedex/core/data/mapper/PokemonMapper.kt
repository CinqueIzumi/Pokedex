package nl.rhaydus.pokedex.core.data.mapper

import nl.rhaydus.pokedex.core.domain.model.Pokemon
import nl.rhaydus.pokedex.features.pokemon_display.data.model.PokemonEntity

fun Pokemon.toPokemonEntity(): PokemonEntity = PokemonEntity(
    id = this.id,
    name = this.name,
    mainType = this.mainType,
    artworkUrl = this.artworkUrl,
    weight = this.weight,
    height = this.height,
    abilities = this.abilities,
    description = this.description,
    malePercentage = this.malePercentage,
    secondaryType = this.secondaryType,
    favorite = this.favorite,
)

fun PokemonEntity.toPokemon(): Pokemon = Pokemon(
    id = this.id,
    name = this.name,
    mainType = this.mainType,
    artworkUrl = this.artworkUrl,
    weight = this.weight,
    height = this.height,
    abilities = this.abilities,
    description = this.description,
    malePercentage = this.malePercentage,
    secondaryType = this.secondaryType,
    favorite = this.favorite,
)