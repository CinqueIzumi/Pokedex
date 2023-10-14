package nl.rhaydus.pokedex.core.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import nl.rhaydus.pokedex.core.data.repository.SplashScreenRepositoryImpl
import nl.rhaydus.pokedex.core.domain.repository.SplashScreenRepository
import nl.rhaydus.pokedex.features.pokemon_display.data.repositories.PokemonRepositoryImpl
import nl.rhaydus.pokedex.features.pokemon_display.domain.repositories.PokemonRepository

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindPokemonRepository(
        pokemonRepositoryImpl: PokemonRepositoryImpl
    ): PokemonRepository

    @Binds
    abstract fun bindSplashScreenRepository(
        splashScreenRepository: SplashScreenRepositoryImpl
    ): SplashScreenRepository
}