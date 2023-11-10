package nl.rhaydus.pokedex.features.splash.domain.exception

sealed class SplashScreenException : Exception() {
    data object EmptyResponseBody : SplashScreenException()
    data object FailedResponse : SplashScreenException()
}