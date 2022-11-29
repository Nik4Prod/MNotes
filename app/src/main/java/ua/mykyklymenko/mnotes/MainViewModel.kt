package ua.mykyklymenko.mnotes

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ua.mykyklymenko.mnotes.model.Note
import ua.mykyklymenko.mnotes.utils.TYPE_DATABASE
import ua.mykyklymenko.mnotes.utils.TYPE_FIREBASE
import ua.mykyklymenko.mnotes.utils.TYPE_ROOM

class MainViewModel(application: Application): AndroidViewModel(application) {

    val readTest: MutableLiveData<List<Note>> by lazy {
        MutableLiveData<List<Note>>()
    }

    val dbType: MutableLiveData<String> by lazy {
        MutableLiveData<String>(TYPE_ROOM)
    }


    init {
        readTest.value =
            when(dbType.value){
                TYPE_ROOM -> {
                    listOf<Note>(
                        Note(title = "Title 1", subtitle = "Subtitle"),
                        Note(title = "Title 2", subtitle = "Subtitle"),
                        Note(title = "Title 3", subtitle = "Subtitle"),
                        Note(title = "Title 4", subtitle = "Subtitle"),
                        Note(title = "Title 5", subtitle = "Subtitle"),
                    )
                }
                TYPE_FIREBASE -> listOf()
                else -> listOf()
            }
    }

    fun initDatabase(type: String){
        dbType.value = type
        Log.d("check Data", "MainViewModel init database with type: $type")
    }
}

class MainViewModelFactory(private val application: Application): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)){
            return MainViewModel(application = application) as T
        }
        throw java.lang.IllegalArgumentException("Unknown ViewModel Class")
    }
}