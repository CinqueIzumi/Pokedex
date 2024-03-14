package nl.rhaydus.pokedex.features.pokemon_display.data.helper

import androidx.sqlite.db.SimpleSQLiteQuery

object QueryHelper {
    private var containsConditions: Boolean = false

    fun getQuery(
        nameOrId: String?,
        isFavorite: Boolean?,
        mainType: String?
    ): SimpleSQLiteQuery {
        var queryString = ""
        var args = mutableListOf<Any>()

        // Prevent caching from previous query building
        containsConditions = false

        // Start the assembly of the query string
        queryString += "SELECT * FROM pokemonentity"

        // Add the name or id filter if required
        nameOrId?.let { givenNameOrId ->
            // Check whether the given query is numerical (id) or a pokemon name
            when (givenNameOrId.toIntOrNull()) {
                null -> {
                    queryString += addCondition() + " poke_name LIKE ?"
                    args.add("%$givenNameOrId%")
                }

                else -> {
                    queryString += addCondition() + " id = ?"
                    args.add(givenNameOrId)
                }
            }

            containsConditions = true
        }

        // Add the favorites filter if required
        isFavorite?.let { fav ->
            queryString += addCondition() + " favorite = ?"
            args.add(fav)
        }

        // Add the main type filter if required
        mainType?.let { givenMainType ->
            if (mainType == "All") return@let

            queryString += addCondition() + " poke_main_type = ?"
            args.add(givenMainType)
        }

        // End of the query
        queryString += ";"

        return SimpleSQLiteQuery(queryString, args.toList().toTypedArray())
    }

    private fun addCondition(): String = if (containsConditions) " AND" else {
        containsConditions = true
        " WHERE"
    }
}