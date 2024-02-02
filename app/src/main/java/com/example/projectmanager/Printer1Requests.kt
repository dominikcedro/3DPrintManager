package com.example.projectmanager

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import android.widget.Toast.makeText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projectmanager.DatePickFragment.RequestDetailsFragment
import com.example.projectmanager.RequestsDayRecycler.RequestModel
import com.example.projectmanager.RequestsDayRecycler.Requests_RecyclerViewAdapter
import com.google.firebase.Firebase
import com.google.firebase.Timestamp
import com.google.firebase.firestore.firestore


class Printer1Requests : AppCompatActivity(), Requests_RecyclerViewAdapter.OnItemClickListener {
    val db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_printer1_requests)
        val spinnerSort = findViewById<Spinner>(R.id.spinner_sort)

// Create an ArrayAdapter using the custom spinner item layout
val adapter = ArrayAdapter.createFromResource(
    this,
    R.array.sort_array,
    R.layout.spinner_item
)

// Specify the layout to use when the list of choices appears
adapter.setDropDownViewResource(R.layout.spinner_dropdown_item)

// Apply the adapter to the spinner
spinnerSort.adapter = adapter


        lateinit var requestsRecyclerView: RecyclerView
        lateinit var requestsAdapter: Requests_RecyclerViewAdapter

        requestsRecyclerView = findViewById(R.id.requestsRecyclerView1)
        requestsRecyclerView.layoutManager = LinearLayoutManager(this)
        requestsAdapter = Requests_RecyclerViewAdapter(ArrayList<RequestModel>(),this)
        requestsRecyclerView.adapter = requestsAdapter

        makeText(this, "Fetching data...", Toast.LENGTH_SHORT).show()

//        db.collection("requests")
//            .get()
//            .addOnSuccessListener { result ->
//                val requests = ArrayList<RequestModel>()
//                for (document in result) {
//                    val author = document.getString("author")
//                    val subject = document.getString("subject")
//                    val startDate = document.getString("startDate")
//                    val starTime = document.getString("startTime")
//                    val endDate = document.getString("endDate")
//                    val endTime = document.getString("endTime")
//                    val filament = document.getString("filament")
//                    val startDateTime = document.get("startDateTime")
//                    val endDateTime = document.get("endDateTime")
//                    val currentDate = document.getTimestamp("postDate")
//                    val startDateTimestamp = document.getTimestamp("startTimestamp")
//
//
//                    val request = RequestModel(author, subject, startDate, endDate, starTime,
//                        endTime, filament, startDateTime, endDateTime, currentDate, startDateTimestamp)
//                    requests.add(request)
//                }
//                requests.sortBy { it.postDate }
//                requestsAdapter.dataSet = requests
//                requestsAdapter.notifyDataSetChanged()
//            }
//            .addOnFailureListener { exception ->
//                makeText(this, "Error getting documents.", Toast.LENGTH_SHORT).show()
//            }
        // when spinnerSort is selected delete all requests and fetch new ones
        spinnerSort.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                val sortOption = parent.getItemAtPosition(position).toString()

                // Clear the requests
                requestsAdapter.dataSet.clear()
                requestsAdapter.notifyDataSetChanged()

                // Fetch the requests again
                db.collection("requests")
                    .get()
                    .addOnSuccessListener { result ->
                        val requests = ArrayList<RequestModel>()
                        for (document in result) {
                            val author = document.getString("author")
                            val email = document.getString("email")
                            val subject = document.getString("subject")
                            val startDate = document.getString("startDate")
                            val starTime = document.getString("startTime")
                            val endDate = document.getString("endDate")
                            val endTime = document.getString("endTime")
                            val filament = document.getString("filament")
                            val startDateTime = document.get("startDateTime")
                            val endDateTime = document.get("endDateTime")
                            val currentDate = document.getTimestamp("postDate")
                            val startDateTimestamp = document.getTimestamp("startTimestamp")
                            val endDateTimestamp = document.getTimestamp("endTimestamp")
                            val request = RequestModel(
                                author,
                                email,
                                subject,
                                startDate,
                                endDate,
                                starTime,
                                endTime,
                                filament,
                                startDateTime,
                                endDateTime,
                                currentDate,
                                startDateTimestamp,
                                endDateTimestamp
                            )
                            requests.add(request)
                        }

                        // Sort the requests based on the selected option
                        if (sortOption == "Sort by Start Date") {
                            requests.sortBy { it.startTimestamp }
                        } else if (sortOption == "Sort by Post Date") {
                            requests.sortBy { it.postDate }
                        }

                        // Update the adapter
                        requestsAdapter.dataSet = requests
                        requestsAdapter.notifyDataSetChanged()
                    }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Another interface callback
            }
        }
        val addRequestButton = findViewById<Button>(R.id.addRequestButton)
        addRequestButton.setOnClickListener(){
            val intent = Intent(this@Printer1Requests, CreateNewRequest::class.java)
            startActivity(intent)
            finish()
        }
}

    override fun onItemClick(position: Int) {
        // open fragment RequestDetailsFragment
        val detailsFragment = RequestDetailsFragment()
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.detailsFragmentContainer, detailsFragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}