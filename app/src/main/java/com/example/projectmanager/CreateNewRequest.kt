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
import com.example.projectmanager.DateTimeOperation.DateTime
import com.example.projectmanager.RequestsDayRecycler.RequestModel
import com.example.projectmanager.TimePickFragment.OnTimeChosenListener
import com.example.projectmanager.TimePickFragment.TimePickFragment
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
        val requestStartTime = findViewById<TextView>(R.id.etStartTime)
        val requestEndTime = findViewById<TextView>(R.id.etEndTime)
        val requestFilament = findViewById<EditText>(R.id.etFilament)
        val buttonCreateRequest = findViewById<Button>(R.id.submitRequestButton)
        var dateTimeCorrect = false
        var subjectCorrect = false
        var filamentCorrect = false
        var startDateTime: DateTime? = null
        var endDateTime: DateTime? = null


        // start date picking
        requestStartDate.setOnClickListener {
            val datePickFragment = DatePickFragment()
            datePickFragment.onDateChosenListener = object : OnDateChosenListener {
                override fun onDateChosen(day: Int, month: Int, year: Int) {
                    val dateString = "${month + 1}-$day-$year" // month is 0-indexed so add 1
                    requestStartDate.setText(dateString)
                }
            }
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, datePickFragment)
                .commit()
        }

        // start time picking
        requestStartTime.setOnClickListener {
            val timePickFragment = TimePickFragment()
            timePickFragment.onTimeChosenListener = object : OnTimeChosenListener {
                override fun onTimeChosen(hour: Int, minute: Int) {
                    val timeString = "$hour:$minute"
                    requestStartTime.setText(timeString)


                }
            }
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, timePickFragment)
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

        // end time picking
        requestEndTime.setOnClickListener {
            val timePickFragment = TimePickFragment()
            timePickFragment.onTimeChosenListener = object : OnTimeChosenListener {
                override fun onTimeChosen(hour: Int, minute: Int) {
                    val timeString = "$hour:$minute"
                    requestEndTime.setText(timeString)
                }
            }
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, timePickFragment)
                .commit()
        }



        buttonCreateRequest.setOnClickListener {
            // proceed only if dateTimeCorrect is true
            var filamentCorrect = isFilamentValid()
            var subjectCorrect = isSubjectValid()
            // DateTime check
            val startDateTime = getStartDateTime()
            val endDateTime = getEndDateTime()
            if (startDateTime != null && endDateTime != null) {
                if (startDateTime > endDateTime) {
                    Toast.makeText(this, "Start date must be before end date", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                else {
                    dateTimeCorrect = true
                }
            }
            else {
                Toast.makeText(this, "Please enter valid date and time", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (!dateTimeCorrect && !subjectCorrect && !filamentCorrect){
                Toast.makeText(this, "Please enter valid information", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val subject = requestSub.text.toString()
            val startDate = requestStartDate.text.toString()
            val endDate = requestEndDate.text.toString()
            val filament = requestFilament.text.toString()

            val request = RequestModel(subject, startDate, endDate)
//            db.collection("requests")
//                .document()
//                .set(request)
//                .addOnSuccessListener {
//                    Toast.makeText(this, "Request created successfully", Toast.LENGTH_SHORT).show()
//                }
            finish()
        }
    }
    fun isSubjectValid(): Boolean {
        val requestSub = findViewById<EditText>(R.id.etSubject)
        val subject = requestSub.text.toString()
        return subject.length > 0
    }

    fun isFilamentValid(): Boolean {
        val requestFilament = findViewById<EditText>(R.id.etFilament)
        val filament = requestFilament.text.toString()
        return filament.length > 0
    }

    fun getStartDateTime(): DateTime? {
        val requestStartDate = findViewById<TextView>(R.id.etStartDate)
        val requestStartTime = findViewById<TextView>(R.id.etStartTime)

        val startDate = requestStartDate.text.toString()
        val startTime = requestStartTime.text.toString()

        if (startDate.isNotEmpty() && startTime.isNotEmpty()) {
            val dateParts = startDate.split("-")
            val timeParts = startTime.split(":")

            val day = dateParts[1].toInt()
            val month = dateParts[0].toInt()
            val year = dateParts[2].toInt()

            val hour = timeParts[0].toInt()
            val minute = timeParts[1].toInt()

            return DateTime(day, month, year, hour, minute)
        }

        return null
    }

    fun getEndDateTime(): DateTime? {
        val requestEndDate = findViewById<TextView>(R.id.etEndDate)
        val requestEndTime = findViewById<TextView>(R.id.etEndTime)

        val endDate = requestEndDate.text.toString()
        val endTime = requestEndTime.text.toString()

        if (endDate.isNotEmpty() && endTime.isNotEmpty()) {
            val dateParts = endDate.split("-")
            val timeParts = endTime.split(":")

            val day = dateParts[1].toInt()
            val month = dateParts[0].toInt()
            val year = dateParts[2].toInt()

            val hour = timeParts[0].toInt()
            val minute = timeParts[1].toInt()

            return DateTime(day, month, year, hour, minute)
        }

        return null
    }


}