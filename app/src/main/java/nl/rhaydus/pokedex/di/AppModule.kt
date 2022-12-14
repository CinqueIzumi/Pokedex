package nl.rhaydus.pokedex.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import nl.rhaydus.pokedex.R
import nl.rhaydus.pokedex.features.pokemon_display.data.dao.PokemonDao
import nl.rhaydus.pokedex.features.pokemon_display.data.data_sources.LocalPokemonDataSource
import nl.rhaydus.pokedex.features.pokemon_display.data.data_sources.LocalPokemonDataSourceImpl
import nl.rhaydus.pokedex.features.pokemon_display.data.data_sources.RemotePokemonDataSource
import nl.rhaydus.pokedex.features.pokemon_display.data.data_sources.RemotePokemonDataSourceImpl
import nl.rhaydus.pokedex.features.pokemon_display.data.database.AppDatabase
import nl.rhaydus.pokedex.features.pokemon_display.data.network.PokemonApiService
import nl.rhaydus.pokedex.features.pokemon_display.data.repositories.PokemonRepositoryImpl
import nl.rhaydus.pokedex.features.pokemon_display.domain.repositories.PokemonRepository
import nl.rhaydus.pokedex.features.pokemon_display.domain.use_cases.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideRoomDatabase(@ApplicationContext context: Context): AppDatabase = Room
        .databaseBuilder(context, AppDatabase::class.java, "poke-database")
        .fallbackToDestructiveMigration()
        .build()

    @Provides
    @Singleton
    fun providePokemonDao(db: AppDatabase): PokemonDao = db.pokemonDao()

    @Provides
    @Singleton
    fun provideRetrofit(@ApplicationContext context: Context): Retrofit = Retrofit
        .Builder()
        .baseUrl(context.getString(R.string.api_base_url))
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    @Singleton
    fun providePokemonApiService(retrofit: Retrofit): PokemonApiService =
        retrofit.create(PokemonApiService::class.java)

    @Provides
    @Singleton
    fun provideRemoteDataSource(
        pokemonApiService: PokemonApiService,
        @ApplicationContext context: Context
    ): RemotePokemonDataSource =
        RemotePokemonDataSourceImpl(pokemonApiService, context)

    @Provides
    @Singleton
    fun provideLocalDataSource(
        pokemonDao: PokemonDao,
        @ApplicationContext context: Context
    ): LocalPokemonDataSource =
        LocalPokemonDataSourceImpl(pokemonDao, context)

    @Provides
    @Singleton
    fun providePokemonRepository(
        remotePokemonDataSource: RemotePokemonDataSource,
        localPokemonDataSource: LocalPokemonDataSource
    ): PokemonRepository =
        PokemonRepositoryImpl(remotePokemonDataSource, localPokemonDataSource)

    @Provides
    @Singleton
    fun provideGetAllPokemon(pokemonRepository: PokemonRepository): GetAllPokemon =
        GetAllPokemon(pokemonRepository)

    @Provides
    @Singleton
    fun provideFavoritePokemon(pokemonRepository: PokemonRepository): FavoritePokemon =
        FavoritePokemon(pokemonRepository)

    @Provides
    @Singleton
    fun provideUnFavoritePokemon(pokemonRepository: PokemonRepository): UnFavoritePokemon =
        UnFavoritePokemon(pokemonRepository)

    @Provides
    @Singleton
    fun provideGetPokemonWithFilter(pokemonRepository: PokemonRepository): GetPokemonWithFilter =
        GetPokemonWithFilter(pokemonRepository)
}