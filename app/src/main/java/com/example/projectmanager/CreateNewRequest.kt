package com.example.projectmanager

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import android.widget.Toast.makeText
import com.example.projectmanager.DatePickFragment.DatePickFragment
import com.example.projectmanager.RequestsDayRecycler.RequestModel
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

class CreateNewRequest : AppCompatActivity() {
    val db = Firebase.firestore
    val dates = mutableListOf<Triple<Int, Int, Int>>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_new_request)
    val requestSub = findViewById<EditText>(R.id.etSubject)
    val requestStartDate = findViewById<EditText>(R.id.etStartDate)
    val requestEndDate = findViewById<EditText>(R.id.etEndDate)
    val requestFilament = findViewById<EditText>(R.id.etFilament)
        val buttonCreateRequest = findViewById<Button>(R.id.submitRequestButton)

        requestStartDate.setOnClickListener {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, DatePickFragment())
                .commit()
        }

        buttonCreateRequest.setOnClickListener {
            val fragment =
                supportFragmentManager.findFragmentById(R.id.fragmentContainer) as DatePickFragment
            val bundle = fragment.arguments
            val day = bundle?.getInt("day")
            val month = bundle?.getInt("month")
            val year = bundle?.getInt("year")



            val dateString = if (day != null && month != null && year != null) {
                "${month + 1}/$day/$year" // month is 0-indexed so we add 1
            } else {
                ""
            }

            when {
                requestStartDate.text.isEmpty() -> {
                    requestStartDate.setText(dateString)
                }
                requestEndDate.text.isEmpty() -> {
                    requestEndDate.setText(dateString)
                }
                else -> {
                    Toast.makeText(this, "Both start and end dates are already set.", Toast.LENGTH_SHORT).show()
                }
            }

            // Add the date to the list
            if (day != null && month != null && year != null) {
                dates.add(Triple(day, month, year))
            }

        }}}
//            val subject = requestSub.text.toString()
//            val startDate = requestStartDate.text.toString()
//            val endDate = requestEndDate.text.toString()
//            val filament = requestFilament.text.toString()
//            val request = RequestModel(subject, startDate, endDate)
//            db.collection("requests")
//                .document()
//                .set(request)
//                .addOnSuccessListener {
//                makeText(this, "Request created successfully", Toast.LENGTH_SHORT).show()
//
