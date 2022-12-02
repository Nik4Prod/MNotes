package ua.mykyklymenko.mnotes.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import ua.mykyklymenko.mnotes.MainViewModel
import ua.mykyklymenko.mnotes.model.Note
import ua.mykyklymenko.mnotes.navigation.NavRoute
import ua.mykyklymenko.mnotes.ui.uielements.ChangeNote
import ua.mykyklymenko.mnotes.utils.Constants.Keys.NOTE_SUBTITLE
import ua.mykyklymenko.mnotes.utils.Constants.Keys.NOTE_TITLE


@Composable
fun AddScreen(
    navHostController: NavHostController,
    mViewModel: MainViewModel
) {

    ChangeNote(
        confirmText = "Add note",
        onConfirmClicked = { title, subtitle ->
            mViewModel.addNote(note = Note(title = title, subtitle = subtitle)) {
                navHostController.popBackStack()
                navHostController.navigate(NavRoute.Main.route)
            }
        }
    )

}

@Preview
@Composable
fun AddPreview() {
    AddScreen(navHostController = rememberNavController(), viewModel())
}