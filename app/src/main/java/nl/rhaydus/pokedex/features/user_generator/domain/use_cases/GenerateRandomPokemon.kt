package nl.rhaydus.pokedex.features.user_generator.domain.use_cases

import android.util.Log
import nl.rhaydus.pokedex.features.user_generator.domain.model.Pokemon
import nl.rhaydus.pokedex.features.user_generator.domain.repositories.UserRepository

class GenerateRandomPokemon(private val repository: UserRepository) {
    suspend operator fun invoke(): Pokemon? {
        val result = repository.getRandomPokemon()

        result.fold(
            onSuccess = { pokemon ->
                return pokemon
            },
            onFailure = { error ->
                Log.e("CustomTag", "Something went wrong! $error")
                return null
            })
    }
}