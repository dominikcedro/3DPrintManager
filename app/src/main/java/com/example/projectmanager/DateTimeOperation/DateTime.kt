package com.example.projectmanager.DateTimeOperation

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDateTime

data class DateTime(val day: Int, val month: Int, val year: Int, val hour: Int, val minute: Int) : Comparable<DateTime> {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun compareTo(other: DateTime): Int {
        val thisDateTime = LocalDateTime.of(year, month, day, hour, minute)
        val otherDateTime = LocalDateTime.of(other.year, other.month, other.day, other.hour, other.minute)
        return thisDateTime.compareTo(otherDateTime)
    }
}