package nl.rhaydus.pokedex.core.domain.util

import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

object HomeWidgetManager {
    private val _bottomNavBarVisible = MutableStateFlow(true)
    val bottomNavBarVisible = _bottomNavBarVisible.asStateFlow()

    private val _notificationTrayColor = MutableStateFlow<Color?>(null)
    val notificationTrayColor = _notificationTrayColor.asStateFlow()

    fun showBottomNavBar(show: Boolean) = _bottomNavBarVisible.update { show }

    fun setTrayColor(color: Color?) = _notificationTrayColor.update { color }
}