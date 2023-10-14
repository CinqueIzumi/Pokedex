package nl.rhaydus.pokedex.core.data.mapper

import nl.rhaydus.pokedex.core.data.network.response.GlobalPokemonInformationResponse
import nl.rhaydus.pokedex.core.data.network.response.GlobalPokemonResponse
import nl.rhaydus.pokedex.features.pokemon_display.data.model.PokemonEntity
import nl.rhaydus.pokedex.features.pokemon_display.domain.model.Pokemon

fun GlobalPokemonResponse.toPokemonList(): List<Pokemon> = this.pokemon.map { it.toPokemon() }

fun GlobalPokemonInformationResponse.toPokemon(): Pokemon {
    val id: Int = this.url
        .removePrefix("https://pokeapi.co/api/v2/pokemon/")
        .removeSuffix("/")
        .toInt()

    return Pokemon(name = this.name.replaceFirstChar { it.uppercase() }, id = id)
}

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
    secondaryType = this.secondaryType
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
    secondaryType = this.secondaryType
)