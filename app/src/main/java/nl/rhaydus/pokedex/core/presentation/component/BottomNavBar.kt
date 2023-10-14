package nl.rhaydus.pokedex.core.presentation.component

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.ramcosta.composedestinations.utils.allDestinations
import com.ramcosta.composedestinations.utils.startDestination
import nl.rhaydus.pokedex.core.presentation.navigation.NavBarDestination
import nl.rhaydus.pokedex.core.presentation.theme.DefaultPreviews

@Composable
fun BottomNavBar(
    modifier: Modifier = Modifier,
    navController: NavController = rememberNavController(),
    showNavBar: Boolean = true
) {
    if (showNavBar) NavigationBar(
        modifier = modifier,
        containerColor = MaterialTheme.colors.surface,
        tonalElevation = 0.dp,
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination
        NavBarDestination.values().forEach { destination ->
            NavigationBarItem(
                icon = {
                    Icon(
                        painterResource(id = destination.icon),
                        contentDescription = stringResource(id = destination.label),
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    unselectedIconColor = Color.Gray,
                    unselectedTextColor = Color.Gray,
                    selectedIconColor = if (isSystemInDarkTheme()) MaterialTheme.colors.onSurface else Color.Cyan,
                    selectedTextColor = if (isSystemInDarkTheme()) MaterialTheme.colors.onSurface else Color.Cyan,
                    indicatorColor = MaterialTheme.colors.surface,
                ),
                label = {
                    Text(
                        stringResource(destination.label),
                        style = MaterialTheme.typography.body1
                    )
                },
                selected = currentDestination?.hierarchy?.any { it.route == destination.direction.route } == true,
                onClick = {
                    // Make sure that there is no navigation when the currently active screen is
                    // the same as the bottomNavBar screen that the user is trying to open
                    if (navController.currentDestination?.route == destination.direction.startDestination.route) {
                        return@NavigationBarItem
                    }

                    val navigationFromChildToParentNavBarDestination = destination
                        .direction
                        .allDestinations
                        .map { it.route }
                        .contains(navController.currentDestination?.route)

                    var navigateTo = destination.direction.route

                    if (navigationFromChildToParentNavBarDestination) {
                        navController.popBackStack(
                            destination.direction.startDestination.route,
                            inclusive = true
                        )

                        navigateTo = destination.direction.startDestination.route
                    }

                    navController.navigate(navigateTo) {
                        // Pop up to the start destination of the graph to
                        // avoid building up a large stack of destinations
                        // on the back stack as users select items
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }

                        // Avoid multiple copies of the same destination when
                        // re-selecting the same item
                        launchSingleTop = true

                        // Restore state when re-selecting a previously selected item
                        restoreState = true
                    }
                },
            )
        }
    }
}

@DefaultPreviews
@Composable
fun BottomNavBarPreview() {
    MaterialTheme {
        BottomNavBar()
    }
}