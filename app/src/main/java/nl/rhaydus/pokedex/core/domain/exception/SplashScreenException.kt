package nl.rhaydus.pokedex.core.domain.exception

sealed class SplashScreenException : Exception() {
    data object EmptyResponseBody : SplashScreenException()
    data object FailedResponse : SplashScreenException()
}