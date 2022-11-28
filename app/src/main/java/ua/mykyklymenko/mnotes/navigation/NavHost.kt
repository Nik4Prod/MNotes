package ua.mykyklymenko.mnotes.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ua.mykyklymenko.mnotes.screens.Add
import ua.mykyklymenko.mnotes.screens.Main
import ua.mykyklymenko.mnotes.screens.Note
import ua.mykyklymenko.mnotes.screens.Start

sealed class NavRoute(val route: String){
    object Start: NavRoute("start_screen")
    object Main: NavRoute("start_screen")
    object Add: NavRoute("start_screen")
    object Note: NavRoute("start_screen")
}


@Composable
fun NotesNavHost() {
    val navHostController = rememberNavController()

    NavHost(navController = navHostController, startDestination = NavRoute.Start.route){
        composable(NavRoute.Start.route){ Start(navHostController = navHostController) }
        composable(NavRoute.Main.route){ Main(navHostController = navHostController) }
        composable(NavRoute.Add.route){ Add(navHostController = navHostController) }
        composable(NavRoute.Note.route){ Note(navHostController = navHostController) }

    }
}