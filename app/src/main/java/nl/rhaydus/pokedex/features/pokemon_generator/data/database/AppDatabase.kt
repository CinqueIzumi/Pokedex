package nl.rhaydus.pokedex.features.pokemon_generator.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import nl.rhaydus.pokedex.features.pokemon_generator.data.dao.PokemonDao
import nl.rhaydus.pokedex.features.pokemon_generator.data.model.PokemonEntity

@Database(entities = [PokemonEntity::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun pokemonDao(): PokemonDao
}