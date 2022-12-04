package ua.mykyklymenko.mnotes.model.firebase

import androidx.lifecycle.LiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import ua.mykyklymenko.mnotes.model.Note
import ua.mykyklymenko.mnotes.navigation.NavRoute

class AllNotesLiveData: LiveData<List<Note>>() {
    private val mAuth = FirebaseAuth.getInstance()
    private val database = Firebase.database("https://mnote-app-default-rtdb.europe-west1.firebasedatabase.app/").reference
        .child(mAuth.currentUser?.uid.toString())

    private val listener = object : ValueEventListener{
        override fun onDataChange(snapshot: DataSnapshot) {
            val notes = mutableListOf<Note>()
            snapshot.children.map {
                notes.add(it.getValue(Note::class.java)?: Note())
            }
            value = notes
        }

        override fun onCancelled(error: DatabaseError) {}
    }

    override fun onActive() {
        database.addValueEventListener(listener)
        super.onActive()
    }

    override fun onInactive() {
        database.removeEventListener(listener)
        super.onInactive()
    }
}