package ua.mykyklymenko.mnotes.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch
import ua.mykyklymenko.mnotes.MainViewModel
import ua.mykyklymenko.mnotes.R
import ua.mykyklymenko.mnotes.navigation.NavRoute
import ua.mykyklymenko.mnotes.ui.uielements.Logging
import ua.mykyklymenko.mnotes.utils.LOGIN
import ua.mykyklymenko.mnotes.utils.PASSWORD
import ua.mykyklymenko.mnotes.utils.TYPE_FIREBASE
import ua.mykyklymenko.mnotes.utils.TYPE_ROOM


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun StartScreen(
    navHostController: NavHostController,
    mViewModel: MainViewModel
) {

    val bottomSheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
    val coroutinesScope = rememberCoroutineScope()


    ModalBottomSheetLayout(
        modifier = Modifier,
        sheetState = bottomSheetState,
        sheetElevation = 5.dp,
        sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        sheetContent = {
            Surface() {
                Column(modifier = Modifier
                    .fillMaxWidth()
                    .padding(32.dp)) {
                    Logging(confirmText = stringResource(id = R.string.logging_in),
                        onConfirmClicked = { login,password->
                            LOGIN = login
                            PASSWORD = password
                            mViewModel.initDatabase(TYPE_FIREBASE){
                                mViewModel.radAllNotes()
                                navHostController.navigate(NavRoute.Main.route)
                            }
                    })
                }
            }
        },
        content = {
            Surface() {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(text = stringResource(id = R.string.database_ask))
                    Button(
                        onClick = {
                            mViewModel.initDatabase(TYPE_ROOM){
                                navHostController.navigate(NavRoute.Main.route)
                            }
                        },
                        modifier = Modifier
                            .width(200.dp)
                            .padding(vertical = 8.dp)
                    ) {
                        Text(text = stringResource(id = R.string.database_room))
                    }
                    Button(
                        onClick = {
                                  coroutinesScope.launch {
                                      bottomSheetState.show()
                                  }
                        },
                        modifier = Modifier
                            .width(200.dp)
                            .padding(vertical = 8.dp)
                    ) {
                        Text(text = stringResource(id = R.string.database_firebase))
                    }
                }
            }
        })



}

@Preview
@Composable
fun StartPreview() {
    StartScreen(navHostController = rememberNavController(), viewModel())
}