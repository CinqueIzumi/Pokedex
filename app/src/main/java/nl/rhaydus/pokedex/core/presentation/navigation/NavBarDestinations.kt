package nl.rhaydus.pokedex.core.presentation.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.ramcosta.composedestinations.spec.NavGraphSpec
import nl.rhaydus.pokedex.NavGraphs
import nl.rhaydus.pokedex.R

enum class NavBarDestination(
    val direction: NavGraphSpec,
    @DrawableRes val icon: Int,
    @StringRes val label: Int
) {
    Overview(NavGraphs.overview, R.drawable.ic_pokeball, R.string.app_name),
    Test(NavGraphs.test, R.drawable.ic_height, R.string.app_name),
}