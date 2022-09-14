package nl.rhaydus.pokedex.features.pokemon_generator.data.mapper

import nl.rhaydus.pokedex.features.pokemon_generator.data.model.PokemonApiModel
import nl.rhaydus.pokedex.features.pokemon_generator.domain.model.Pokemon

fun PokemonApiModel.toPokemon(): Pokemon = Pokemon(this.name, this.id, this.sprites.url)