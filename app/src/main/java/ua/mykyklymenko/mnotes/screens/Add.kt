package ua.mykyklymenko.mnotes.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import ua.mykyklymenko.mnotes.MainViewModel
import ua.mykyklymenko.mnotes.R
import ua.mykyklymenko.mnotes.model.Note
import ua.mykyklymenko.mnotes.navigation.NavRoute
import ua.mykyklymenko.mnotes.ui.uielements.ChangeNote


@Composable
fun AddScreen(
    navHostController: NavHostController,
    mViewModel: MainViewModel
) {

    ChangeNote(
        confirmText = stringResource(id = R.string.add_new_note),
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