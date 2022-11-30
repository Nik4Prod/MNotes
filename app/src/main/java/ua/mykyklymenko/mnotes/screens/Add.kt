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


@Composable
fun AddScreen(
    navHostController: NavHostController,
    mViewModel: MainViewModel
) {

    var title by remember { mutableStateOf("") }
    var subtitle by remember { mutableStateOf("") }

    val focusManager = LocalFocusManager.current
    var isButtonEnabled by remember { mutableStateOf(false) }


    Scaffold() {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Add new note",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(8.dp),
                textAlign = TextAlign.Center
            )
            OutlinedTextField(
                value = title,
                onValueChange = {
                    title = it
                    isButtonEnabled = title.isNotEmpty() && subtitle.isNotEmpty()
                },
                label = { Text(text = "Tittle") },
                isError = title.isEmpty(),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                keyboardActions = KeyboardActions( onNext = {focusManager.moveFocus(FocusDirection.Down)})
            )
            OutlinedTextField(
                value = subtitle,
                onValueChange = {
                    subtitle = it
                    isButtonEnabled = title.isNotEmpty() && subtitle.isNotEmpty()
                },
                label = { Text(text = "Subtitle") },
                isError = subtitle.isEmpty()
            )
            Button(
                modifier = Modifier.padding(top = 16.dp),
                enabled = isButtonEnabled,
                onClick = {
                    mViewModel.addNote(note = Note(title = title, subtitle = subtitle)){
                        navHostController.navigate(NavRoute.Main.route)
                    }
                },

            ) {
                Text(text = "Add note")
            }
        }
    }
}

@Preview
@Composable
fun AddPreview() {
    AddScreen(navHostController = rememberNavController(), viewModel())
}