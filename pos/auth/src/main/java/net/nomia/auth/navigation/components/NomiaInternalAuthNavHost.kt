package net.nomia.auth.navigation.components

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import net.nomia.auth.ui.InternalAuthDestination
import net.nomia.auth.ui.internal.InternalAuthScreen
import net.nomia.auth.ui.route

@Composable
public fun NomiaInternalAuthNavHost(
    navHostController: NavHostController = rememberNavController(),
) {
    NavHost(navController = navHostController, startDestination = InternalAuthDestination.route) {
        composable(route = InternalAuthDestination.route) {
            InternalAuthScreen()
        }
    }
}
