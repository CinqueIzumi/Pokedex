package nl.rhaydus.pokedex.features.splash.domain.usecase

import nl.rhaydus.pokedex.features.splash.domain.repository.SplashScreenRepository
import javax.inject.Inject

class InitializeAllPokemon @Inject constructor(
    private val splashScreenRepository: SplashScreenRepository
) {
    suspend operator fun invoke(): Result<Unit> = runCatching {
        splashScreenRepository.initializeAllPokemon()
    }
}