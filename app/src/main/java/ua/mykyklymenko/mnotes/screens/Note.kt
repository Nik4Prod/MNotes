package ua.mykyklymenko.mnotes.screens

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch
import ua.mykyklymenko.mnotes.MainViewModel
import ua.mykyklymenko.mnotes.model.Note
import ua.mykyklymenko.mnotes.navigation.NavRoute
import ua.mykyklymenko.mnotes.ui.theme.LightGray
import ua.mykyklymenko.mnotes.ui.uielements.ChangeNote
import ua.mykyklymenko.mnotes.ui.uielements.NoteCard
import ua.mykyklymenko.mnotes.utils.Constants
import ua.mykyklymenko.mnotes.utils.Constants.Keys.DELETE
import ua.mykyklymenko.mnotes.utils.Constants.Keys.NONE
import ua.mykyklymenko.mnotes.utils.Constants.Keys.NOTE_SUBTITLE
import ua.mykyklymenko.mnotes.utils.Constants.Keys.NOTE_TITLE
import ua.mykyklymenko.mnotes.utils.Constants.Keys.UPDATE


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun NoteScreen(
    navHostController: NavHostController,
    mViewModel: MainViewModel
) {


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
                            mViewModel.updateNote(note = Note(id = note.id,title = title, subtitle = subtitle)){
                                coroutinesScope.launch {
                                    bottomSheetState.hide()
                                }
                            }
                            mViewModel.pushNote(Note(id = note.id,title = title, subtitle = subtitle))
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
                                            
                                                val snackbarResult = scaffoldState.snackbarHostState.showSnackbar(
                                                    message = "All information about this note will be erased",
                                                    actionLabel = "Delete"
                                                )
                                                when(snackbarResult){
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