package ua.mykyklymenko.mnotes.screens

import android.app.Application
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch
import ua.mykyklymenko.mnotes.MainViewModel
import ua.mykyklymenko.mnotes.MainViewModelFactory
import ua.mykyklymenko.mnotes.model.Note
import ua.mykyklymenko.mnotes.navigation.NavRoute
import ua.mykyklymenko.mnotes.ui.uielements.ChangeNote
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
                    Logging(confirmText = "Log In",
                        onConfirmClicked = { login,password->
                            LOGIN = login
                            PASSWORD = password
                            mViewModel.initDatabase(TYPE_FIREBASE){

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
                    Text(text = "What will we use?")
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
                        Text(text = "Room database")
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
                        Text(text = "Firebase database")
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