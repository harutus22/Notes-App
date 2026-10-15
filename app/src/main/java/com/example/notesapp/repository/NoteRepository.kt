package com.example.notesapp.repository

import android.content.Context
import com.example.notesapp.room.Note
import com.example.notesapp.room.NoteDatabase

class NoteRepository(context: Context) {

    private val noteDao = NoteDatabase.getDatabase(context).noteDao()

    fun getAllNotes() = noteDao.getAllNotes()

    suspend fun insertNote(note: Note) {
        noteDao.insertNote(note)
    }

    suspend fun deleteNote(note: Note) {
        noteDao.delete(note)
    }
}