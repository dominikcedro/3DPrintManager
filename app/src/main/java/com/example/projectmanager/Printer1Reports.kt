package com.example.projectmanager

import android.annotation.SuppressLint
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.projectmanager.Firestore.CommentData
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class Printer1Reports : AppCompatActivity() {
    val db = Firebase.firestore

    @SuppressLint("NewApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_printer1_reports)

        val commentText = findViewById<EditText>(R.id.editTextComment)
        val commentButton = findViewById<Button>(R.id.postTheComment)
        var username:String? = null
        var email:String? = null
        var formattedDateTime: String = ""
        db.collection("usersData")
            .document(FirebaseAuth.getInstance().currentUser?.email.toString())
            .get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    username = document.getString("name")
                    email = document.getString("email")
                }


        commentButton.setOnClickListener {
            val comment = commentText.text.toString()
            val name = username.toString()
            val mail = email.toString()
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val current = LocalDateTime.now()
                val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                formattedDateTime = current.format(formatter)
            } else {
                // API 26 and below
            }
            val commentData = CommentData("Ultimaker 2+", formattedDateTime,name,mail, comment, 0 )
            db.collection("comments").document(formattedDateTime)
                    .set(commentData)

            }

    }
}}