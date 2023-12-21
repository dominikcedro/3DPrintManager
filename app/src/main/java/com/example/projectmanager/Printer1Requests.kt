package com.example.projectmanager

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projectmanager.RequestsDayRecycler.RequestModel
import com.example.projectmanager.RequestsDayRecycler.Requests_RecyclerViewAdapter
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

class Printer1Requests : AppCompatActivity() {
    val db = Firebase.firestore
    private lateinit var requestsRecyclerView: RecyclerView
    private lateinit var requestsAdapter: Requests_RecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_printer1_requests)

        requestsRecyclerView = findViewById(R.id.requestsRecyclerView1)
        requestsRecyclerView.layoutManager = LinearLayoutManager(this)
        requestsAdapter = Requests_RecyclerViewAdapter(ArrayList<RequestModel>())
        requestsRecyclerView.adapter = requestsAdapter

        db.collection("requests")
            .get()
            .addOnSuccessListener { result ->
                val requests = ArrayList<RequestModel>()
                for (document in result) {
                    val username = document.getString("username")
                    val subject = document.getString("subject")
                    val startDate = document.getString("startDate")
                    val endDate = document.getString("endDate")
                    val totalTime = document.getString("totalTime")
                    val requestModel = RequestModel(username, subject, startDate, endDate, totalTime)
                    requests.add(requestModel)
                }
                requestsAdapter.dataSet = requests
                requestsAdapter.notifyDataSetChanged()


    }
        // go to create request activity
        val addRequestButton = findViewById<Button>(R.id.addRequestButton)
        addRequestButton.setOnClickListener {
            val intent = Intent(this, CreateRequest::class.java)
            startActivity(intent)
        }
}}