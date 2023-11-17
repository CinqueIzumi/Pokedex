package nl.rhaydus.pokedex.core.presentation.navigation

import com.ramcosta.composedestinations.annotation.NavGraph
import com.ramcosta.composedestinations.annotation.RootNavGraph

@RootNavGraph
@NavGraph
annotation class BottomNavGraph(val start: Boolean = false)

@BottomNavGraph(start = true)
@NavGraph
annotation class OverviewNavGraph(val start: Boolean = false)

@BottomNavGraph
@NavGraph
annotation class RegionNavGraph(val start: Boolean = false)

@BottomNavGraph
@NavGraph
annotation class FavoritesNavGraph(val start: Boolean = false)

@BottomNavGraph
@NavGraph
annotation class AccountNavGraph(val start: Boolean = false)