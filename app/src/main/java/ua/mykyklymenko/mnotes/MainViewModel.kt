package ua.mykyklymenko.mnotes

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ua.mykyklymenko.mnotes.database.firebase.FirebaseRepository
import ua.mykyklymenko.mnotes.database.room.AppRoomDatabase
import ua.mykyklymenko.mnotes.database.room.repository.RoomRepository
import ua.mykyklymenko.mnotes.model.Note
import ua.mykyklymenko.mnotes.utils.*

class MainViewModel(application: Application): AndroidViewModel(application) {

    private val context = application

    private var currentNote: Note = Note(title = "", subtitle = "")

    fun pullNote(): Note = currentNote
    fun pushNote(note: Note){
        currentNote = note
        Log.d("PushMethod", "Pushed note:$note")
    }


    fun initDatabase(type: String, onSuccess: ()-> Unit){
        Log.d(DEBUG_TAG, "MainViewModel:  init database with type: $type")
        when(type){
            TYPE_ROOM ->{
                val dao = AppRoomDatabase.getInstance(context = context).getRoomDao()
                REPOSITORY = RoomRepository(dao)
                DB_TYPE = TYPE_ROOM
                onSuccess()
            }
            TYPE_FIREBASE->{
                REPOSITORY = FirebaseRepository()
                REPOSITORY.connectToDatabase(
                    onSuccess = {
                        DB_TYPE = TYPE_FIREBASE
                        onSuccess()
                    },
                    onFail = {
                        Log.d("Firebase Auth", it)
                    }
                )

            }
        }
    }

    fun addNote(note:Note, onSuccess: () -> Unit){
        viewModelScope.launch(Dispatchers.IO){
            REPOSITORY.create(note = note){
                viewModelScope.launch(Dispatchers.Main){
                    onSuccess()
                }
            }
        }
    }

    fun updateNote(note: Note, onSuccess: () -> Unit){
        viewModelScope.launch(Dispatchers.IO){
            REPOSITORY.update(note = note){
                viewModelScope.launch(Dispatchers.Main){
                    onSuccess()
                }
            }
        }
    }
    fun deleteNote(note: Note, onSuccess: () -> Unit){
        viewModelScope.launch(Dispatchers.IO){
            REPOSITORY.delete(note = note){
                viewModelScope.launch(Dispatchers.Main){
                    onSuccess()
                }
            }
        }
    }

    fun signOut(onSuccess: () -> Unit){
        when(DB_TYPE){
            TYPE_FIREBASE,TYPE_ROOM -> {
                REPOSITORY.signOut()
                DB_TYPE = ""
                onSuccess()
            }
        }
    }


    fun radAllNotes() = REPOSITORY.readAll



}
