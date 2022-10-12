package nl.rhaydus.pokedex.features.pokemon_display.data.mapper

import android.content.res.Resources
import nl.rhaydus.pokedex.R
import nl.rhaydus.pokedex.features.pokemon_display.data.model.PokemonApiModel
import nl.rhaydus.pokedex.features.pokemon_display.data.model.PokemonEntity
import nl.rhaydus.pokedex.features.pokemon_display.domain.model.Pokemon

fun List<PokemonEntity>.toPokemonList(): List<Pokemon> {
    val pokeList = mutableListOf<Pokemon>()

    this.forEach { pokeEntity ->
        pokeList.add(pokeEntity.toPokemon())
    }

    return pokeList.toList()
}

fun PokemonApiModel.toPokemon(): Pokemon {
    val newTypeList = mutableListOf<String>()
    this.types.forEach { pokemonTypeEntry ->
        val type = pokemonTypeEntry.type.name.replaceFirstChar { it.uppercase() }
        newTypeList.add(type)
    }

    return Pokemon(
        name = this.name.replaceFirstChar { it.uppercase() },
        id = this.id,
        imageUrl = this.sprites.other.artwork.artworkUrl ?: Resources.getSystem()
            .getString(R.string.default_egg_sprite),
        types = newTypeList.toList(),
        weight = this.weight / 10,
        height = this.height / 10,
        this.stats[0].baseStat,
        this.stats[1].baseStat,
        this.stats[2].baseStat,
        this.stats[3].baseStat,
        this.stats[4].baseStat,
        this.stats[5].baseStat,
        favorite = 0 // Pokemon are not favorites by default
    )
}


fun Pokemon.toPokemonEntity(): PokemonEntity = PokemonEntity(
    id = this.id,
    pokeName = this.name,
    pokeImageUrl = this.imageUrl,
    pokeMainType = this.types[0],
    pokeSecondaryType = if (this.types.size > 1) this.types[1] else null,
    pokeWeight = this.weight,
    pokeHeight = this.height,
    pokeStatHp = this.hpStat,
    pokeStatAtk = this.atkStat,
    pokeStatDef = this.defStat,
    pokeStatSpAtk = this.spAtkStat,
    pokeStatSpDef = this.spDefStat,
    pokeStatSpd = this.spdStat,
    favorite = this.favorite
)

fun PokemonEntity.toPokemon(): Pokemon {
    val types = mutableListOf<String>()
    types.add(this.pokeMainType)
    this.pokeSecondaryType?.let { type -> types.add(type) }

    return Pokemon(
        name = this.pokeName,
        id = this.id,
        imageUrl = this.pokeImageUrl,
        types = types.toList(),
        weight = this.pokeWeight,
        height = this.pokeHeight,
        hpStat = this.pokeStatHp,
        atkStat = this.pokeStatAtk,
        defStat = this.pokeStatDef,
        spAtkStat = this.pokeStatSpAtk,
        spDefStat = this.pokeStatSpDef,
        spdStat = this.pokeStatSpd,
        favorite = this.favorite
    )
}