package com.example.notesapp.utils

import android.os.Bundle
import com.example.notesapp.room.Note
import com.google.firebase.Firebase
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.analytics

object CustomFirebaseLogger {
    private var firebaseAnalytics: FirebaseAnalytics = Firebase.analytics

    fun logCustomEvent(eventName: Action, note: Note) {
        val params = Bundle().apply {
            putString(note.name, note.description)
        }

        firebaseAnalytics.logEvent(eventName.action, params)
    }

    enum class Action(val action: String){
        ADD_NOTE("add_note"),
        REMOVE_NOTE("remove_note"),
        VIEW_NOTE("view_note");
    }
}