package com.example.projectmanager

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import android.widget.Toast.makeText
import com.example.projectmanager.RequestsDayRecycler.RequestModel
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

class CreateNewRequest : AppCompatActivity() {
    val db = Firebase.firestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_new_request)
    val requestSub = findViewById<EditText>(R.id.etSubject)
    val requestStartDate = findViewById<EditText>(R.id.etStartDate)
    val requestEndDate = findViewById<EditText>(R.id.etEndDate)
    val requestFilament = findViewById<EditText>(R.id.etFilament)
        val buttonCreateRequest = findViewById<Button>(R.id.submitRequestButton)

        buttonCreateRequest.setOnClickListener {
            val subject = requestSub.text.toString()
            val startDate = requestStartDate.text.toString()
            val endDate = requestEndDate.text.toString()
            val filament = requestFilament.text.toString()
            val request = RequestModel(subject, startDate, endDate)
            db.collection("requests")
                .document()
                .set(request)
                .addOnSuccessListener {
                makeText(this, "Request created successfully", Toast.LENGTH_SHORT).show()


        }

    }
}}