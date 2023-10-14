package nl.rhaydus.pokedex.core.data.datasource

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import nl.rhaydus.pokedex.core.data.constant.HIGHEST_POKEMON_ID
import nl.rhaydus.pokedex.core.data.mapper.toPokemonEntity
import nl.rhaydus.pokedex.features.pokemon_display.data.dao.PokemonDao
import nl.rhaydus.pokedex.features.pokemon_display.domain.model.Pokemon
import javax.inject.Inject

class LocalSplashScreenDataSourceImpl @Inject constructor(
    private val pokemonDao: PokemonDao
) : LocalSplashScreenDataSource {
    override suspend fun addPokemon(pokemon: List<Pokemon>) {
        withContext(Dispatchers.IO) {
            pokemon.forEach { pokemonDao.insert(it.toPokemonEntity()) }
        }
    }

    override suspend fun isLocalDataSourceComplete(): Boolean {
        val result = withContext(Dispatchers.IO) {
            pokemonDao.getDatabaseSize()
        }

        return result == HIGHEST_POKEMON_ID
    }
}