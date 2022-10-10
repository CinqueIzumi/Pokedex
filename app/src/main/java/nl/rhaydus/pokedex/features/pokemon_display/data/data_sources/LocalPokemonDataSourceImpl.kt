package nl.rhaydus.pokedex.features.pokemon_display.data.data_sources

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import nl.rhaydus.pokedex.R
import nl.rhaydus.pokedex.features.pokemon_display.data.dao.PokemonDao
import nl.rhaydus.pokedex.features.pokemon_display.data.mapper.toPokemon
import nl.rhaydus.pokedex.features.pokemon_display.data.mapper.toPokemonEntity
import nl.rhaydus.pokedex.features.pokemon_display.domain.model.Pokemon
import javax.inject.Inject

class LocalPokemonDataSourceImpl @Inject constructor(
    private val pokemonDao: PokemonDao,
    @ApplicationContext private val context: Context
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

    override suspend fun getSpecificPokemon(pokemonId: Int) =
        pokemonDao.getPokemonById(pokemonId).toPokemon()


    override suspend fun getAllPokemon() =
        getPokemonUntilId(context.resources.getInteger(R.integer.highest_pokemon_id))


    override suspend fun addPokemons(pokes: List<Pokemon>) {
        for (poke in pokes) {
            pokemonDao.insert(poke.toPokemonEntity())
        }
    }

    override suspend fun isLocalDataComplete() =
        pokemonDao.getDatabaseSize() == context.resources.getInteger(R.integer.highest_pokemon_id)

    private fun updatePokemon(pokemon: Pokemon, favorite: Int): Boolean {
        val pokemonToSave = pokemon.copy(favorite = favorite)
        pokemonDao.updatePokemon(pokemonToSave.toPokemonEntity())

        return true
    }

    override suspend fun favoritePokemon(pokemon: Pokemon): Boolean =
        updatePokemon(pokemon, 1)

    override suspend fun unFavoritePokemon(pokemon: Pokemon): Boolean =
        updatePokemon(pokemon, 0)
}