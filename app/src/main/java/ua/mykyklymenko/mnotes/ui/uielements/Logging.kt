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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ua.mykyklymenko.mnotes.utils.Constants
import ua.mykyklymenko.mnotes.utils.Constants.Keys.LOGGING

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun Logging(
    modifier: Modifier = Modifier,
    login: String = "",
    password: String = "",
    confirmText: String = "Confirm",
    onConfirmClicked: (String,String) -> Unit = {_,_-> }
) {
    var loginVal by remember { mutableStateOf(login) }
    var passwordVal by remember { mutableStateOf(password) }

    val focusManager = LocalFocusManager.current
    var isButtonEnabled by remember { mutableStateOf(false) }

    val keyboardController = LocalSoftwareKeyboardController.current

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = LOGGING,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(8.dp),
            textAlign = TextAlign.Center
        )
        OutlinedTextField(
            value = loginVal,
            onValueChange = {
                loginVal = it
                isButtonEnabled = loginVal.isNotEmpty() && passwordVal.isNotEmpty()
            },
            label = { Text(text = Constants.Keys.LOGGING) },
            isError = loginVal.isEmpty(),
            placeholder = { Text(text = Constants.Keys.YOUR_LOGIN) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email,imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Down) })
        )



        OutlinedTextField(
            value = passwordVal,
            onValueChange = {
                passwordVal = it
                isButtonEnabled = loginVal.isNotEmpty() && passwordVal.isNotEmpty()
            },
            label = { Text(text = Constants.Keys.PASSWORD) },
            placeholder = { Text(text = Constants.Keys.YOUR_PASSWORD) },
            isError = passwordVal.isEmpty(),
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password,imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(
                onNext = {
                    onConfirmClicked(loginVal,passwordVal)
                    keyboardController?.hide()
                })
        )
        Button(
            modifier = Modifier.padding(top = 16.dp),
            enabled = isButtonEnabled,
            onClick = {
                keyboardController?.hide()
                onConfirmClicked(loginVal,passwordVal)
            },
            ) {
            Text(text = confirmText)
        }
    }
}