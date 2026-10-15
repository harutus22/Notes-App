package com.example.notesapp.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import android.os.Parcelable

@Entity(tableName = "note_table")
@kotlinx.parcelize.Parcelize
data class Note(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val description: String,
    val createdAt: String
): Parcelable