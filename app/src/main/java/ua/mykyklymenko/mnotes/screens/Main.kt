package ua.mykyklymenko.mnotes.screens

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import ua.mykyklymenko.mnotes.MainViewModel
import ua.mykyklymenko.mnotes.navigation.NavRoute
import ua.mykyklymenko.mnotes.ui.theme.LightGray
import ua.mykyklymenko.mnotes.ui.uielements.NoteCard



@Composable
fun MainScreen(
    navHostController: NavHostController,
    mViewModel: MainViewModel
) {

    val notes = mViewModel.radAllNotes().observeAsState(listOf()).value

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navHostController.navigate(NavRoute.Add.route)
                },
                backgroundColor = LightGray

            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = null)
            }
        }
    ) {

        LazyColumn(
            modifier = Modifier.padding(it)
        ){
            items(notes){ note ->
                NoteCard(
                    note = note,
                    onNoteClicked = {
                        mViewModel.pushNote(note)
                        navHostController.navigate(NavRoute.Note.route)


                    }
                )
            }
        }

    }
}

@Preview(showSystemUi = true)
@Composable
fun MainPreview() {
    MainScreen(navHostController = rememberNavController(), viewModel())
}