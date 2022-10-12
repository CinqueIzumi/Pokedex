package nl.rhaydus.pokedex.features.pokemon_display.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import nl.rhaydus.pokedex.features.pokemon_display.data.dao.PokemonDao
import nl.rhaydus.pokedex.features.pokemon_display.data.model.PokemonEntity

@Database(entities = [PokemonEntity::class], version = 6)
abstract class AppDatabase : RoomDatabase() {
    abstract fun pokemonDao(): PokemonDao
}