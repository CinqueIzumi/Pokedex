package nl.rhaydus.pokedex.core.domain.util

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

object BottomNavBarManager {
    private val _bottomNavBarVisible = MutableStateFlow(true)
    val bottomNavBarVisible = _bottomNavBarVisible.asStateFlow()

    fun showBottomNavBar(show: Boolean) = _bottomNavBarVisible.update { show }
}