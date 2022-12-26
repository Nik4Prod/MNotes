package ua.mykyklymenko.mnotes.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch
import ua.mykyklymenko.mnotes.MainViewModel
import ua.mykyklymenko.mnotes.R
import ua.mykyklymenko.mnotes.model.Note
import ua.mykyklymenko.mnotes.navigation.NavRoute
import ua.mykyklymenko.mnotes.ui.theme.LightGray
import ua.mykyklymenko.mnotes.ui.uielements.ChangeNote
import ua.mykyklymenko.mnotes.ui.uielements.NoteCard
import ua.mykyklymenko.mnotes.utils.Constants.Keys.DELETE
import ua.mykyklymenko.mnotes.utils.Constants.Keys.UPDATE
import ua.mykyklymenko.mnotes.utils.DB_TYPE
import ua.mykyklymenko.mnotes.utils.TYPE_FIREBASE
import ua.mykyklymenko.mnotes.utils.TYPE_ROOM


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun NoteScreen(
    navHostController: NavHostController,
    mViewModel: MainViewModel
) {

    val message = stringResource(id = R.string.all_info_will_erased)
    val deleteMessage = stringResource(id = R.string.delete)

    val bottomSheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
    val coroutinesScope = rememberCoroutineScope()

    var note by remember {
        mutableStateOf(mViewModel.pullNote())
    }
    val scaffoldState = rememberScaffoldState()


    Scaffold(scaffoldState = scaffoldState, ) {
        ModalBottomSheetLayout(
            modifier = Modifier.padding(it),
            sheetState = bottomSheetState,
            sheetElevation = 5.dp,
            sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
            sheetContent = {
                Surface() {
                    Column(modifier = Modifier
                        .fillMaxWidth()
                        .padding(32.dp)) {
                        ChangeNote(title = note.title, subtitle = note.subtitle, onConfirmClicked = { title, subtitle ->

                            val noteUpdated = when(DB_TYPE){
                                TYPE_ROOM -> Note(id = note.id,title = title, subtitle = subtitle)
                                TYPE_FIREBASE -> Note(firebaseId = note.firebaseId,title = title, subtitle = subtitle)
                                else -> mViewModel.pullNote()
                            }

                            mViewModel.updateNote(note = noteUpdated){
                                coroutinesScope.launch {
                                    bottomSheetState.hide()
                                }
                            }
                            mViewModel.pushNote(noteUpdated)
                            note = mViewModel.pullNote()
                        })
                    }
                }
            }
        ){
            Scaffold(
                modifier = Modifier.fillMaxSize(),
                topBar = {
                    TopAppBar(
                        title = {
                            IconButton(onClick = { navHostController.navigate(NavRoute.Main.route) }) {
                                Icon(
                                    imageVector = Icons.Default.ArrowBack,
                                    contentDescription = "back",
                                    modifier = Modifier
                                        .padding(8.dp)
                                        .clip(CircleShape)
                                )
                            }
                        },
                        backgroundColor = LightGray
                    )
                },
                content = {
                    Column(
                        modifier = Modifier
                            .padding(it)
                            .fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Surface(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(32.dp)
                        ) {
                            Column(
                                modifier = Modifier.padding(vertical = 8.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                NoteCard(note = note)
                                Row(
                                    modifier = Modifier
                                        .padding(horizontal = 32.dp)
                                        .fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceAround
                                ) {
                                    Button(
                                        modifier = Modifier.padding(top = 16.dp),
                                        onClick = {
                                            coroutinesScope.launch {
                                                bottomSheetState.show()
                                            }
                                        }
                                    ) {
                                        Text(text = UPDATE)
                                    }
                                    Button(
                                        modifier = Modifier.padding(top = 16.dp),
                                        onClick = {

                                            coroutinesScope.launch {

                                                val snackBarResult = scaffoldState.snackbarHostState.showSnackbar(
                                                    message = message,
                                                    actionLabel = deleteMessage
                                                )
                                                when(snackBarResult){
                                                    SnackbarResult.ActionPerformed -> {
                                                        mViewModel.deleteNote(note){
                                                            navHostController.popBackStack()
                                                            navHostController.navigate(NavRoute.Main.route)
                                                            mViewModel.pushNote(Note(title = "", subtitle = ""))
                                                        }

                                                    }
                                                    else -> {}
                                                }
                                            }
                                        }
                                    ) {
                                        Text(text = DELETE)
                                    }
                                }
                            }
                        }
                    }
                }
            )
        }
    }




}




@Preview
@Composable
fun NotePreview() {
    NoteScreen(
        navHostController = rememberNavController(),
        mViewModel = viewModel()
    )
}