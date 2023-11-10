package nl.rhaydus.pokedex.features.splash.domain.repository

interface SplashScreenRepository {
    suspend fun initializeAllPokemon()
}