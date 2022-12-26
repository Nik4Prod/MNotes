package ua.mykyklymenko.mnotes.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

import ua.mykyklymenko.mnotes.MainViewModel
import ua.mykyklymenko.mnotes.screens.AddScreen
import ua.mykyklymenko.mnotes.screens.MainScreen
import ua.mykyklymenko.mnotes.screens.NoteScreen
import ua.mykyklymenko.mnotes.screens.StartScreen

import ua.mykyklymenko.mnotes.utils.Constants.Screens.ADD_SCREEN
import ua.mykyklymenko.mnotes.utils.Constants.Screens.MAIN_SCREEN
import ua.mykyklymenko.mnotes.utils.Constants.Screens.NOTE_SCREEN
import ua.mykyklymenko.mnotes.utils.Constants.Screens.START_SCREEN

sealed class NavRoute(val route: String){
    object Start: NavRoute(START_SCREEN)
    object Main: NavRoute(MAIN_SCREEN)
    object Add: NavRoute(ADD_SCREEN)
    object Note: NavRoute(NOTE_SCREEN)
}


@Composable
fun NotesNavHost(
    mViewModel: MainViewModel,
    navHostController: NavHostController
) {


    NavHost(navController = navHostController, startDestination = NavRoute.Start.route){
        composable(NavRoute.Start.route){ StartScreen(navHostController = navHostController, mViewModel) }
        composable(NavRoute.Main.route){ MainScreen(navHostController = navHostController, mViewModel) }
        composable(NavRoute.Add.route){ AddScreen(navHostController = navHostController, mViewModel) }
        composable(NavRoute.Note.route){
            NoteScreen(navHostController = navHostController, mViewModel)
        }

    }
}