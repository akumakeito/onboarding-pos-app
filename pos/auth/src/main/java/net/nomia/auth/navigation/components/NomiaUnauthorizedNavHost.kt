package net.nomia.auth.navigation.components

import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import net.nomia.auth.ui.*
import net.nomia.auth.ui.external.ExternalAuthScreen

@Composable
public fun NomiaUnauthorizedNavHost(
    navHostController: NavHostController = rememberNavController(),
    widthSizeClass: WindowWidthSizeClass,
) {

    NavHost(
        navController = navHostController,
        startDestination = ExternalAuthDestination.route
    ) {
        composable(
            route = ExternalAuthDestination.route,
            arguments = ExternalAuthDestination.argumentList
        ) {
            ExternalAuthScreen(navController = navHostController)
        }
    }
}
