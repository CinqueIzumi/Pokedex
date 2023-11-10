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
    Overview(NavGraphs.overview, R.drawable.ic_poke_ball_nav, R.string.navigation_pokedex),
    Regions(NavGraphs.region, R.drawable.ic_poke_pin_nav, R.string.navigation_regions),
    Favorites(NavGraphs.favorites, R.drawable.ic_poke_pin_nav, R.string.navigation_favorites),
    Account(NavGraphs.account, R.drawable.ic_poke_pin_nav, R.string.navigation_account),
}