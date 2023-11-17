package nl.rhaydus.pokedex.features.splash.data.mapper

import nl.rhaydus.pokedex.core.domain.model.Pokemon
import nl.rhaydus.pokedex.features.splash.data.network.response.GlobalPokemonInformationResponse
import nl.rhaydus.pokedex.features.splash.data.network.response.GlobalPokemonResponse

fun GlobalPokemonResponse.toPokemonList(): List<Pokemon> = this.pokemon.map { it.toPokemon() }

fun GlobalPokemonInformationResponse.toPokemon(): Pokemon {
    val id: Int = this.url
        .removePrefix("https://pokeapi.co/api/v2/pokemon/")
        .removeSuffix("/")
        .toInt()

    return Pokemon(name = this.name.replaceFirstChar { it.uppercase() }, id = id)
}