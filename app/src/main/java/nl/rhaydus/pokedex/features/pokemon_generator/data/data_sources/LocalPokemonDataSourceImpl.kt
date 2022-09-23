package nl.rhaydus.pokedex.features.pokemon_generator.data.data_sources

import nl.rhaydus.pokedex.core.HIGHEST_POKEMON_ID
import nl.rhaydus.pokedex.features.pokemon_generator.data.dao.PokemonDao
import nl.rhaydus.pokedex.features.pokemon_generator.data.mapper.toPokemon
import nl.rhaydus.pokedex.features.pokemon_generator.data.mapper.toPokemonEntity
import nl.rhaydus.pokedex.features.pokemon_generator.data.model.PokemonEntity
import nl.rhaydus.pokedex.features.pokemon_generator.domain.model.Pokemon
import timber.log.Timber
import java.lang.Exception
import javax.inject.Inject

class LocalPokemonDataSourceImpl @Inject constructor(
    private val pokemonDao: PokemonDao
) : LocalPokemonDataSource {
    override suspend fun getPokemonUntilId(id: Int): List<Pokemon> {
        val pokeList = mutableListOf<Pokemon>()

        for (i in 1..id) {
            pokeList.add(getSpecificPokemon(i))
        }

        return pokeList
    }

    override suspend fun getRandomPokemon(): Pokemon {
        val randomId = (0..905).shuffled().last()
        return getSpecificPokemon(randomId)
    }

    override suspend fun getSpecificPokemon(pokemonId: Int): Pokemon {
        return pokemonDao.getPokemonById(pokemonId).toPokemon()
    }

    override suspend fun getAllPokemon(): List<Pokemon> {
        return getPokemonUntilId(HIGHEST_POKEMON_ID)
    }

    override suspend fun addPokemons(pokes: List<Pokemon>) {
        for (poke in pokes) {
            pokemonDao.insert(poke.toPokemonEntity())
        }
    }
}