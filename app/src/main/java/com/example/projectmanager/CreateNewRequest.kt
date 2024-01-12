package com.example.projectmanager

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.projectmanager.DatePickFragment.DatePickFragment
import com.example.projectmanager.DatePickFragment.OnDateChosenListener
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
        val requestStartDate = findViewById<TextView>(R.id.etStartDate)
        val requestEndDate = findViewById<TextView>(R.id.etEndDate)
        val requestFilament = findViewById<EditText>(R.id.etFilament)
        val buttonCreateRequest = findViewById<Button>(R.id.submitRequestButton)

        // start date picking
        requestStartDate.setOnClickListener {
            val datePickFragment = DatePickFragment()
            datePickFragment.onDateChosenListener = object : OnDateChosenListener {
                override fun onDateChosen(day: Int, month: Int, year: Int) {
                    val dateString = "${month + 1}/$day/$year" // month is 0-indexed so we add 1
                    requestStartDate.setText(dateString)
                }
            }
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, datePickFragment)
                .commit()
        }

        // end date picking
        requestEndDate.setOnClickListener {
            val datePickFragment = DatePickFragment()
            datePickFragment.onDateChosenListener = object : OnDateChosenListener {
                override fun onDateChosen(day: Int, month: Int, year: Int) {
                    val dateString = "${month + 1}-$day-$year" // month is 0-indexed so add 1
                    requestEndDate.setText(dateString)
                }
            }
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, datePickFragment)
                .commit()
        }

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
                    Toast.makeText(this, "Request created successfully", Toast.LENGTH_SHORT).show()
                }
            finish()
        }
    }
}