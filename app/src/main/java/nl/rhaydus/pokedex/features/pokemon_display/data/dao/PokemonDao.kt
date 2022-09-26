package nl.rhaydus.pokedex.features.pokemon_display.data.dao

import androidx.room.*
import nl.rhaydus.pokedex.features.pokemon_display.data.model.PokemonEntity

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
    fun getFilteredPokemons(input: String): List<PokemonEntity>

    @Query("SELECT * FROM pokemonentity WHERE id LIKE :id")
    fun getPokemonById(id: Int): PokemonEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(pokemon: PokemonEntity)

    @Delete
    fun delete(pokemon: PokemonEntity)

    @Update
    fun updatePokemon(vararg pokemons: PokemonEntity)
}