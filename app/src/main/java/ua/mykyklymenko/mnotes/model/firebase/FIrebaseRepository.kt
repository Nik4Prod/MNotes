package ua.mykyklymenko.mnotes.model.firebase

import android.util.Log
import androidx.lifecycle.LiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import ua.mykyklymenko.mnotes.database.DatabaseRepository
import ua.mykyklymenko.mnotes.model.Note
import ua.mykyklymenko.mnotes.utils.Constants
import ua.mykyklymenko.mnotes.utils.FIREBASE_ID
import ua.mykyklymenko.mnotes.utils.LOGIN
import ua.mykyklymenko.mnotes.utils.PASSWORD

class FirebaseRepository:DatabaseRepository{

    private val mAuth = FirebaseAuth.getInstance()
    override val readAll: LiveData<List<Note>> = AllNotesLiveData()
    private val database = Firebase.database("https://mnote-app-default-rtdb.europe-west1.firebasedatabase.app/").reference
        .child(mAuth.currentUser?.uid.toString())

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
                Log.d("Firebase","note created ${mapNotes.values}")
            }
            .addOnFailureListener { Log.d("On create Note trouble ",it.message.toString()) }
    }

    override suspend fun update(note: Note, onSuccess: () -> Unit) {
        TODO("Not yet implemented")
    }

    override suspend fun delete(note: Note, onSuccess: () -> Unit) {
        TODO("Not yet implemented")
    }

    override fun signOut() {
        mAuth.signOut()
    }

    override fun connectToDatabase(onSuccess: ()-> Unit, onFail: (String)-> Unit) {
        mAuth.signInWithEmailAndPassword(LOGIN, PASSWORD)
            .addOnSuccessListener {
                onSuccess()
                Log.d("Firebase", "Successfully connected to the base")
            }
            .addOnFailureListener {
                mAuth.createUserWithEmailAndPassword(LOGIN, PASSWORD)
                    .addOnSuccessListener {
                        onSuccess()
                        Log.d("Firebase", "Successfully connected to the base")}
                    .addOnFailureListener {
                        onFail(it.message.toString())
                        Log.d("Firebase", "Has a problem on way to connect to the base with message: ${it.message}")
                    }
            }
    }
}