package com.example.notesapp.utils

import android.icu.text.SimpleDateFormat
import android.icu.util.TimeZone
import androidx.room.TypeConverter
import java.util.Date
import java.util.Locale

object SpecificLocalDateTimeConverter {
    private fun getFormatter(): SimpleDateFormat {
        return SimpleDateFormat("MMM d, yyyy h:mm:ss a", Locale.US).apply {
            timeZone = TimeZone.getDefault()
        }
    }

    @TypeConverter
    fun getDate(): String {
        val date = Date()
        return getFormatter().format(date)
    }
}