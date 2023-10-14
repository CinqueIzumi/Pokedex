package nl.rhaydus.pokedex.core.domain.repository

interface SplashScreenRepository  {
    suspend fun initializeAllPokemon()
}