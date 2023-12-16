package com.example.projectmanager

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.annotation.RequiresApi
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class Printer1Reports : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_printer1_reports)

        val commentText = findViewById<EditText>(R.id.editTextComment)
        val commentButton = findViewById<Button>(R.id.postTheComment)


        commentButton.setOnClickListener {
            val comment = commentText.text.toString()
            val time = timestamp()


    }
}