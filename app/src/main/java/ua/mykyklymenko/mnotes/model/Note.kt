package ua.mykyklymenko.mnotes.model


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import ua.mykyklymenko.mnotes.utils.Constants.Keys.NOTE_TABLE


@Entity(tableName = NOTE_TABLE)
data class Note(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    var firebaseId: String = "",
    @ColumnInfo
    val title: String = "",
    @ColumnInfo
    val subtitle: String = ""

)
