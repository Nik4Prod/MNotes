package ua.mykyklymenko.mnotes.ui.uielements

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import ua.mykyklymenko.mnotes.model.Note
import ua.mykyklymenko.mnotes.navigation.NavRoute

@Composable
fun NoteCard(
    note: Note,
    onNoteClicked: () -> Unit = {}

) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp, horizontal = 24.dp)
            .clickable {
                onNoteClicked()
            },
        elevation = 6.dp
    ) {
        Column(
            modifier = Modifier.padding(vertical = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally

        ) {

            // Debug content
//            Text(
//                text = note.id.toString(),
//                fontSize = 15.sp,
//            )
//            Spacer(modifier = Modifier
//                .fillMaxWidth()
//                .background(Color.Black)
//                .height(1.dp))
//            Text(
//                text = note.firebaseId,
//                fontSize = 15.sp,
//            )

            //Regular content
            Text(
                text = note.title,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            Text(text = note.subtitle)
        }
    }
}

