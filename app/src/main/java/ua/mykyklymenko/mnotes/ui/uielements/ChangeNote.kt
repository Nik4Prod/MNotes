package ua.mykyklymenko.mnotes.ui.uielements

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ua.mykyklymenko.mnotes.utils.Constants
import ua.mykyklymenko.mnotes.utils.Constants.Keys.ADD_NEW_NOTE

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ChangeNote(
    modifier: Modifier = Modifier,
    title: String = "",
    subtitle: String = "",
    confirmText: String = "Confirm",
    onConfirmClicked: (String,String) -> Unit = {_,_-> }
) {
    var titleVal by remember { mutableStateOf(title) }
    var subtitleVal by remember { mutableStateOf(subtitle) }

    val focusManager = LocalFocusManager.current
    var isButtonEnabled by remember { mutableStateOf(false) }

    val keyboardController = LocalSoftwareKeyboardController.current

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = ADD_NEW_NOTE,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(8.dp),
            textAlign = TextAlign.Center
        )
        OutlinedTextField(
            value = titleVal,
            onValueChange = {
                titleVal = it
                isButtonEnabled = titleVal.isNotEmpty() && subtitleVal.isNotEmpty()
            },
            label = { Text(text = Constants.Keys.NOTE_TITLE) },
            isError = titleVal.isEmpty(),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Down) })
        )
        OutlinedTextField(
            value = subtitleVal,
            onValueChange = {
                subtitleVal = it
                isButtonEnabled = titleVal.isNotEmpty() && subtitleVal.isNotEmpty()
            },
            label = { Text(text = Constants.Keys.NOTE_SUBTITLE) },
            isError = subtitleVal.isEmpty(),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(
                onNext = {
                    onConfirmClicked(titleVal,subtitleVal)
                    keyboardController?.hide()
                })
        )
        Button(
            modifier = Modifier.padding(top = 16.dp),
            enabled = isButtonEnabled,
            onClick = {
                keyboardController?.hide()
                onConfirmClicked(titleVal,subtitleVal)
            },
            ) {
            Text(text = confirmText)
        }
    }
}