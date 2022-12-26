package ua.mykyklymenko.mnotes.utils

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import ua.mykyklymenko.mnotes.database.DatabaseRepository

const val TYPE_DATABASE = "type_database"
const val TYPE_ROOM = "type_room"
const val TYPE_FIREBASE = "type_firebase"
const val FIREBASE_ID = "firebaseID"
const val FIREBASE_DATABASE_URL = "https://mnote-app-default-rtdb.europe-west1.firebasedatabase.app/"
const val DEBUG_TAG = "Debug"


lateinit var REPOSITORY: DatabaseRepository
var LOGIN: String = ""
var PASSWORD: String = ""
var USER_ID: String = ""
var DB_TYPE by mutableStateOf("")


object Constants{
    object Keys{
        const val NOTE_DATABASE = "notes_database"
        const val NOTE_TABLE = "notes_table"
        const val NOTE_TITLE = "Title"
        const val NOTE_SUBTITLE = "Subtitle"
        const val TITLE = "title"
        const val SUBTITLE = "subtitle"
        const val ADD_NEW_NOTE = "Add new note"
        const val LOGGING = "Log In"
        const val UPDATE = "Update"
        const val DELETE = "Delete"
        const val YOUR_LOGIN = "Your email"
        const val PASSWORD = "Password"
        const val YOUR_PASSWORD = "Your password"

    }
    object Screens{
        const val START_SCREEN = "start_screen"
        const val ADD_SCREEN = "add_screen"
        const val MAIN_SCREEN = "main_screen"
        const val NOTE_SCREEN = "note_screen"

    }
}