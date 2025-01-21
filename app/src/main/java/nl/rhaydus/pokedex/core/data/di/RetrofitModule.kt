package nl.rhaydus.pokedex.core.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import nl.rhaydus.pokedex.BuildConfig
import nl.rhaydus.pokedex.features.pokemon_display.data.network.PokemonApiService
import nl.rhaydus.pokedex.features.splash.data.network.SplashScreenApiService
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {
    @Provides
    @Singleton
    fun getLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = if (BuildConfig.BUILD_TYPE != "release") {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }
    }

    @Provides
    @Singleton
    fun getClient(
        loggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()

    @Provides
    @Singleton
    fun getRetrofit(
        client: OkHttpClient
    ): Retrofit = Retrofit.Builder()
        .client(client)
        .baseUrl("https://pokeapi.co/api/v2/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    @Singleton
    fun getPokemonApiService(
        retrofit: Retrofit,
    ): PokemonApiService = retrofit.create(PokemonApiService::class.java)

    @Provides
    @Singleton
    fun getSplashScreenApiService(
        retrofit: Retrofit,
    ): SplashScreenApiService = retrofit.create(SplashScreenApiService::class.java)
}