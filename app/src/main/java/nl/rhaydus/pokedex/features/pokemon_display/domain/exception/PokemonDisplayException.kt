package nl.rhaydus.pokedex.features.pokemon_display.domain.exception

sealed class PokemonDisplayException : Throwable() {
    data object ApiError : PokemonDisplayException()
    data object EmptyResponseBody : PokemonDisplayException()
}