package nl.rhaydus.pokedex.features.pokemon_generator.data.dao

import androidx.room.*
import nl.rhaydus.pokedex.features.pokemon_generator.data.model.PokemonEntity

@Dao
interface PokemonDao {
    @Query("SELECT * FROM pokemonentity")
    fun getAll(): List<PokemonEntity>

    @Query("DELETE FROM pokemonentity")
    fun deleteAll()

    @Query(
        "SELECT * FROM pokemonentity " +
                "WHERE poke_name LIKE '%' || :input || '%' " +
                "OR poke_main_type LIKE :input " +
                "OR id LIKE :input"
    )
    fun getSpecificPokemon(input: String): List<PokemonEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(pokemon: PokemonEntity)

    @Delete
    fun delete(pokemon: PokemonEntity)

    @Update
    fun updatePokemon(vararg pokemons: PokemonEntity)
}