package ua.mykyklymenko.mnotes.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ua.mykyklymenko.mnotes.screens.AddScreen
import ua.mykyklymenko.mnotes.screens.MainScreen
import ua.mykyklymenko.mnotes.screens.NoteScreen
import ua.mykyklymenko.mnotes.screens.StartScreen

sealed class NavRoute(val route: String){
    object Start: NavRoute("start_screen")
    object Main: NavRoute("main_screen")
    object Add: NavRoute("add_screen")
    object Note: NavRoute("note_screen")
}


@Composable
fun NotesNavHost() {
    val navHostController = rememberNavController()

    NavHost(navController = navHostController, startDestination = NavRoute.Main.route){
        composable(NavRoute.Start.route){ StartScreen(navHostController = navHostController) }
        composable(NavRoute.Main.route){ MainScreen(navHostController = navHostController) }
        composable(NavRoute.Add.route){ AddScreen(navHostController = navHostController) }
        composable(NavRoute.Note.route){ NoteScreen(navHostController = navHostController) }

    }
}