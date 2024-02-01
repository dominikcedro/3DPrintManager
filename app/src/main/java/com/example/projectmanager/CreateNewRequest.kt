package com.example.projectmanager

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.projectmanager.DatePickFragment.DatePickFragment
import com.example.projectmanager.DatePickFragment.OnDateChosenListener
import com.example.projectmanager.DateTimeOperation.DateTime
import com.example.projectmanager.RequestsDayRecycler.RequestModel
import com.example.projectmanager.TimePickFragment.OnTimeChosenListener
import com.example.projectmanager.TimePickFragment.TimePickFragment
import com.google.firebase.Firebase
import com.google.firebase.Timestamp
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import java.util.Calendar

class CreateNewRequest : AppCompatActivity() {
    val db = Firebase.firestore
    val dates = mutableListOf<Triple<Int, Int, Int>>()
    @RequiresApi(Build.VERSION_CODES.O)
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

        // background change for buttons when clicked
        val textFields = arrayOf(requestSub, requestStartDate, requestEndDate, requestStartTime, requestEndTime, requestFilament)

        for (field in textFields) {
            field.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                    // nothing
                }

                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                    // nothing
                }

                override fun afterTextChanged(s: Editable) {
                    if (s.toString().isNotEmpty()) {
                        field.setBackgroundResource(R.drawable.section_field_color)
                    } else {
                        field.setBackgroundResource(R.drawable.section_middle_color)
                    }
                }
            })
        }

        // start date picking
        requestStartDate.setOnClickListener {
            hideKeyboard()
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
            hideKeyboard()
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
            hideKeyboard()
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
            hideKeyboard()
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
            // gate name from usersData collection in firebase based on current users email
            db.collection("usersData")
                .document(Firebase.auth.currentUser!!.uid)
                .get()
                .addOnSuccessListener { result ->
                    val name = result.getString("name")
                    val email = result.getString("email")

                    val subject = requestSub.text.toString()
                    val startDate = requestStartDate.text.toString()
                    val startTime = requestStartTime.text.toString()
                    val endDate = requestEndDate.text.toString()
                    val endTime = requestEndTime.text.toString()
                    val filament = requestFilament.text.toString()
                    val currentTimestamp = Timestamp.now()
                    val startTimestamp = if (startDateTime != null) dateTimeToTimestamp(startDateTime) else null
                    val endTimestamp = if (endDateTime != null) dateTimeToTimestamp(endDateTime) else null

                    db.collection("requests")
                        .get()
                        .addOnSuccessListener { result ->
                            for (document in result) {
                                val startExisting = document.getTimestamp("startTimestamp")
                                val endExisting = document.getTimestamp("endTimestamp")

                                // Check for overlap
                                if (startExisting != null) {
                                    if ((startTimestamp!! in startExisting..endExisting) || (endTimestamp!! in startExisting..endExisting)) {
                                        Toast.makeText(this, "The request overlaps with an existing request.", Toast.LENGTH_SHORT).show()
                                        return@addOnSuccessListener
                                    }
                                }
                            }

                            // If no overlap, create the new request
                            val request = RequestModel(name, subject, startDate, endDate, startTime, endTime, filament, startDateTime, endDateTime, currentTimestamp, startTimestamp, endTimestamp)

                            db.collection("requests")
                                .add(request)
                                .addOnSuccessListener { documentReference ->
                                    Toast.makeText(this, "Request created successfully.", Toast.LENGTH_SHORT).show()
                                    finish()
                                }
                                .addOnFailureListener { e ->
                                    Toast.makeText(this, "Error adding request.", Toast.LENGTH_SHORT).show()
                                }
                        }
                        .addOnFailureListener { exception ->
                            Toast.makeText(this, "Error getting existing requests.", Toast.LENGTH_SHORT).show()
                        }
                }
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
    fun dateTimeToTimestamp(dateTime: DateTime): Timestamp {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.DAY_OF_MONTH, dateTime.day)
        calendar.set(Calendar.MONTH, dateTime.month - 1) // Calendar.MONTH is 0-based
        calendar.set(Calendar.YEAR, dateTime.year)
        calendar.set(Calendar.HOUR_OF_DAY, dateTime.hour)
        calendar.set(Calendar.MINUTE, dateTime.minute)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)

        return Timestamp(calendar.time)
    }
    private fun hideKeyboard() {
        val view = this.currentFocus
        if (view != null) {
            val imm = getSystemService(android.content.Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
}

private operator fun Unit.contains(startTimestamp: Timestamp): Boolean {
    return true
}

private operator fun Timestamp.rangeTo(endExisting: Timestamp?) {

}
