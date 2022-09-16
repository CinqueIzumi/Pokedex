package nl.rhaydus.pokedex.features.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import nl.rhaydus.pokedex.features.core.BASE_URL
import nl.rhaydus.pokedex.features.pokemon_generator.data.data_sources.RemotePokemonDataSource
import nl.rhaydus.pokedex.features.pokemon_generator.data.data_sources.RemotePokemonDataSourceImpl
import nl.rhaydus.pokedex.features.pokemon_generator.data.network.PokemonApiService
import nl.rhaydus.pokedex.features.pokemon_generator.data.repositories.PokemonRepositoryImpl
import nl.rhaydus.pokedex.features.pokemon_generator.domain.repositories.PokemonRepository
import nl.rhaydus.pokedex.features.pokemon_generator.domain.use_cases.GetRandomPokemon
import nl.rhaydus.pokedex.features.pokemon_generator.domain.use_cases.GetSpecificPokemon
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit
            .Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun providePokemonApiService(retrofit: Retrofit): PokemonApiService {
        return retrofit.create(PokemonApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideRemoteDataSource(pokemonApiService: PokemonApiService): RemotePokemonDataSource =
        RemotePokemonDataSourceImpl(pokemonApiService)

    @Provides
    @Singleton
    fun providePokemonRepository(pokemonDataSource: RemotePokemonDataSource): PokemonRepository =
        PokemonRepositoryImpl(pokemonDataSource)

    @Provides
    @Singleton
    fun provideRandomPokemonUseCase(pokemonRepository: PokemonRepository): GetRandomPokemon =
        GetRandomPokemon(pokemonRepository)

    @Provides
    @Singleton
    fun provideSpecificPokemonUseCase(pokemonRepository: PokemonRepository): GetSpecificPokemon =
        GetSpecificPokemon(pokemonRepository)
}