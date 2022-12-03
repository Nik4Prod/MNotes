package ua.mykyklymenko.mnotes.utils

import ua.mykyklymenko.mnotes.database.DatabaseRepository

const val TYPE_DATABASE = "type_database"
const val TYPE_ROOM = "type_room"
const val TYPE_FIREBASE = "type_firebase"

lateinit var REPOSITORY: DatabaseRepository
lateinit var LOGIN: String
lateinit var PASSWORD: String

object Constants{
    object Keys{
        const val NOTE_DATABASE = "notes_database"
        const val NOTE_TABLE = "notes_table"
        const val NOTE_TITLE = "Title"
        const val NOTE_SUBTITLE = "Subtitle"
        const val ADD_NEW_NOTE = "Add new note"
        const val LOGGING = "Log In"
        const val NOTE_ID = "Id"
        const val NONE = "None"
        const val UPDATE = "Update"
        const val DELETE = "Delete"
        const val LOGIN = "Login"
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