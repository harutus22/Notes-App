package com.example.notesapp

import android.app.Application
import com.example.notesapp.repository.NoteRepository

class App: Application() {
    val repository by lazy { NoteRepository( this) }
}