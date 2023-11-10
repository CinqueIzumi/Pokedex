package nl.rhaydus.pokedex.features.pokemon_display.data.dao

import androidx.room.*
import androidx.sqlite.db.SupportSQLiteQuery
import nl.rhaydus.pokedex.features.pokemon_display.data.model.PokemonEntity

@Dao
interface PokemonDao {
    @Query("SELECT * FROM pokemonentity")
    fun getAll(): List<PokemonEntity>

    @Query("DELETE FROM pokemonentity")
    fun deleteAll()

    @RawQuery
    fun getFilteredPokemons(query: SupportSQLiteQuery): List<PokemonEntity>

    @Query("SELECT * FROM pokemonentity WHERE id LIKE :id")
    fun getPokemonById(id: Int): PokemonEntity

    @Query("SELECT COUNT(poke_name) FROM pokemonentity")
    fun getDatabaseSize(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(pokemon: PokemonEntity)

    @Delete
    fun delete(pokemon: PokemonEntity)

    @Update
    fun updatePokemon(vararg pokemon: PokemonEntity)
}