package ua.mykyklymenko.mnotes

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ua.mykyklymenko.mnotes.database.room.AppRoomDatabase
import ua.mykyklymenko.mnotes.database.room.repository.RoomRepository
import ua.mykyklymenko.mnotes.model.Note
import ua.mykyklymenko.mnotes.utils.REPOSITORY
import ua.mykyklymenko.mnotes.utils.TYPE_DATABASE
import ua.mykyklymenko.mnotes.utils.TYPE_FIREBASE
import ua.mykyklymenko.mnotes.utils.TYPE_ROOM

class MainViewModel(application: Application): AndroidViewModel(application) {

    private val context = application

    private var currentNote: Note = Note(title = "", subtitle = "")

    fun pullNote(): Note = currentNote
    fun pushNote(note: Note){
        currentNote = note
    }


    fun initDatabase(type: String, onSuccess: ()-> Unit){
        Log.d("check Data", "MainViewModel init database with type: $type")
        when(type){
            TYPE_ROOM ->{
                val dao = AppRoomDatabase.getInstance(context = context).getRoomDao()
                REPOSITORY = RoomRepository(dao)
                onSuccess()
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



    fun radAllNotes() = REPOSITORY.readAll



}

class MainViewModelFactory(private val application: Application): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)){
            return MainViewModel(application = application) as T
        }
        throw java.lang.IllegalArgumentException("Unknown ViewModel Class")
    }
}