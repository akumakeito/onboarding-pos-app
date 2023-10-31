package net.nomia.pos.ui.navigation.components

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import net.nomia.pos.ui.ManagerScreenDestination
import net.nomia.pos.ui.argumentList
import net.nomia.pos.ui.route

@Composable
internal fun NomiaAuthorizedNavHost(
    navHostController: NavHostController = rememberNavController(),
) {
    NavHost(
        navController = navHostController,
        startDestination = ManagerScreenDestination.route
    ) {
        composable(
            route = ManagerScreenDestination.route,
            arguments = ManagerScreenDestination.argumentList
        ) {
            /**
             * YOUR CODE GOES HERE
             */
        }
    }
}
