package nl.rhaydus.pokedex.features.pokemon_generator.data.dao

import androidx.room.*
import nl.rhaydus.pokedex.features.pokemon_generator.data.model.PokemonEntity

@Dao
interface PokemonDao {
    @Query("SELECT * FROM pokemonentity")
    fun getAll(): List<PokemonEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg pokemons: PokemonEntity)

    @Delete
    fun delete(pokemon: PokemonEntity)

    @Update
    fun updatePokemon(vararg pokemons: PokemonEntity)
}