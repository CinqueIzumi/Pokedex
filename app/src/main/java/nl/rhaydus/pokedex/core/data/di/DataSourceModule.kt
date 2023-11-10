package nl.rhaydus.pokedex.core.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import nl.rhaydus.pokedex.features.pokemon_display.data.datasource.LocalPokemonDataSource
import nl.rhaydus.pokedex.features.pokemon_display.data.datasource.LocalPokemonDataSourceImpl
import nl.rhaydus.pokedex.features.pokemon_display.data.datasource.RemotePokemonDataSource
import nl.rhaydus.pokedex.features.pokemon_display.data.datasource.RemotePokemonDataSourceImpl
import nl.rhaydus.pokedex.features.splash.data.datasource.LocalSplashScreenDataSource
import nl.rhaydus.pokedex.features.splash.data.datasource.LocalSplashScreenDataSourceImpl
import nl.rhaydus.pokedex.features.splash.data.datasource.RemoteSplashScreenDataSource
import nl.rhaydus.pokedex.features.splash.data.datasource.RemoteSplashScreenDataSourceImpl

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {
    @Binds
    abstract fun bindRemotePokemonDataSource(
        remotePokemonDataSourceImpl: RemotePokemonDataSourceImpl
    ): RemotePokemonDataSource

    @Binds
    abstract fun bindLocalPokemonDataSource(
        localPokemonDataSourceImpl: LocalPokemonDataSourceImpl
    ): LocalPokemonDataSource

    @Binds
    abstract fun bindSplashScreenRemotePokemonDataSource(
        splashScreenDataSource: RemoteSplashScreenDataSourceImpl
    ): RemoteSplashScreenDataSource

    @Binds
    abstract fun bindSplashScreenLocalPokemonDataSource(
        splashScreenDataSource: LocalSplashScreenDataSourceImpl
    ): LocalSplashScreenDataSource
}