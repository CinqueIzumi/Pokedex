package com.example.myapplication.features.user_generator.data.mapper

import com.example.myapplication.features.user_generator.data.model.PokemonApiModel
import com.example.myapplication.features.user_generator.domain.model.Pokemon

fun PokemonApiModel.toPokemon(): Pokemon = Pokemon(this.name, this.id)