package nl.rhaydus.pokedex.features.user_generator.data.mapper

import nl.rhaydus.pokedex.features.user_generator.data.model.PokemonApiModel
import nl.rhaydus.pokedex.features.user_generator.domain.model.Pokemon

fun PokemonApiModel.toPokemon(): Pokemon = Pokemon(this.name, this.id)