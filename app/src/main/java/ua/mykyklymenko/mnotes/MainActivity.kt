package ua.mykyklymenko.mnotes

import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import ua.mykyklymenko.mnotes.navigation.NavRoute
import ua.mykyklymenko.mnotes.navigation.NotesNavHost
import ua.mykyklymenko.mnotes.ui.theme.DarkGray
import ua.mykyklymenko.mnotes.ui.theme.LightGray
import ua.mykyklymenko.mnotes.ui.theme.NotesTheme
import ua.mykyklymenko.mnotes.utils.DB_TYPE
import ua.mykyklymenko.mnotes.utils.TYPE_FIREBASE

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            NotesTheme {
                val context = LocalContext.current
                val mViewModel: MainViewModel = viewModel()
                val navHostController = rememberNavController()
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = {
                           Row(
                               modifier = Modifier
                                   .fillMaxSize()
                                   .padding(horizontal = 16.dp),
                               horizontalArrangement = Arrangement.SpaceBetween,
                               verticalAlignment = Alignment.CenterVertically
                           ) {
                               Text(
                                   text = stringResource(id = R.string.app_name),
                                   textAlign = TextAlign.Center,
                                   modifier = Modifier
                                       .padding(vertical = 8.dp)
                               )

                               if (DB_TYPE == TYPE_FIREBASE){
                                   Icon(
                                       modifier = Modifier.clickable{
                                            mViewModel.signOut {
                                                navHostController.popBackStack()
                                                navHostController.navigate(NavRoute.Start.route)
                                            }
                                       },
                                       imageVector = Icons.Default.ExitToApp,
                                       contentDescription = "exit icon"
                                   )
                               }

                           }
                        },
                            backgroundColor = LightGray,
                            contentColor = Color.White,
                            elevation = 0.dp
                    )
                },
                    content = { padding ->
                    Surface(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(padding),
                        color = MaterialTheme.colors.background
                    ) {
                        NotesNavHost(mViewModel, navHostController)
                    }
                }
                )
            }
        }
    }
}



@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DefaultPreview() {

}