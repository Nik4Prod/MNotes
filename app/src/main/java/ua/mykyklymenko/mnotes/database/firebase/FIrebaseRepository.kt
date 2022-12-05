package ua.mykyklymenko.mnotes.database.firebase

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewmodel.CreationExtras
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import ua.mykyklymenko.mnotes.database.DatabaseRepository
import ua.mykyklymenko.mnotes.model.Note
import ua.mykyklymenko.mnotes.utils.*

class FirebaseRepository:DatabaseRepository{

    private val mAuth = FirebaseAuth.getInstance()
    override var readAll: LiveData<List<Note>> = AllNotesLiveData()
    private var database = Firebase.database(FIREBASE_DATABASE_URL).reference
        .child(USER_ID)

    override suspend fun create(note: Note, onSuccess: () -> Unit) {
        val noteId = database.push().key.toString()
        val mapNotes = hashMapOf<String,Any>()



        mapNotes[FIREBASE_ID] = noteId
        mapNotes[Constants.Keys.TITLE] = note.title
        mapNotes[Constants.Keys.SUBTITLE] = note.subtitle

        database.child(noteId)
            .updateChildren(mapNotes)
            .addOnSuccessListener {
                onSuccess()
                Log.d(DEBUG_TAG,"Firebase: note created ${mapNotes.values}")
            }
            .addOnFailureListener { Log.d(DEBUG_TAG,"Firebase: on create Note trouble - " + it.message.toString()) }
    }



    override suspend fun update(note: Note, onSuccess: () -> Unit) {
        val noteId = note.firebaseId
        val mapNotes = hashMapOf<String,Any>()

        mapNotes[FIREBASE_ID] = noteId
        mapNotes[Constants.Keys.TITLE] = note.title
        mapNotes[Constants.Keys.SUBTITLE] = note.subtitle

        database.child(noteId)
            .updateChildren(mapNotes)
            .addOnSuccessListener {
                onSuccess()
                Log.d(DEBUG_TAG,"Firebase: note updated ${mapNotes.values}")
            }
            .addOnFailureListener {
                Log.d(DEBUG_TAG, "Firebase: on update Note trouble - "+ it.message.toString())
            }

    }

    override suspend fun delete(note: Note, onSuccess: () -> Unit) {
        database.child(note.firebaseId).removeValue()
            .addOnSuccessListener {
                Log.d(DEBUG_TAG,"Firebase: note deleted")
                onSuccess()
            }
            .addOnFailureListener {
                Log.d(DEBUG_TAG, "Firebase: on delete Note trouble - "+ it.message.toString())
            }
    }

    override fun signOut() {
        LOGIN = ""
        PASSWORD = ""
        mAuth.signOut()
        DB_TYPE = ""

    }

    override fun connectToDatabase(onSuccess: ()-> Unit, onFail: (String)-> Unit) {
        mAuth.signInWithEmailAndPassword(LOGIN, PASSWORD)
            .addOnSuccessListener {
                USER_ID = mAuth.currentUser?.uid.toString()
                updateDatabase()
                onSuccess()
                Log.d(DEBUG_TAG,"Firebase: Successfully connected to the base")
            }
            .addOnFailureListener {
                Log.d(DEBUG_TAG,"Firebase: Can not login with this login and password, it trying to create new")
                mAuth.createUserWithEmailAndPassword(LOGIN, PASSWORD)
                    .addOnSuccessListener {
                        USER_ID = mAuth.currentUser?.uid.toString()
                        updateDatabase()
                        onSuccess()
                        Log.d(DEBUG_TAG,"Firebase: Successfully created user $LOGIN and connected to the base")
                    }
                    .addOnFailureListener {
                        onFail(it.message.toString())
                        Log.d(DEBUG_TAG,"Firebase: Has a problem on way to connect to the base with message:" + it.message)
                    }
            }
    }

    override fun updateDatabase() {
        readAll = AllNotesLiveData()
        database = Firebase.database(FIREBASE_DATABASE_URL).reference
            .child(USER_ID)
    }
}