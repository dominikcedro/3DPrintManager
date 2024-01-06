package com.example.projectmanager

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import android.widget.Toast.makeText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projectmanager.DatePickFragment.RequestDetailsFragment
import com.example.projectmanager.RequestsDayRecycler.RequestModel
import com.example.projectmanager.RequestsDayRecycler.Requests_RecyclerViewAdapter
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

class Printer1Requests : AppCompatActivity(), Requests_RecyclerViewAdapter.OnItemClickListener {
    val db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_printer1_requests)
        lateinit var requestsRecyclerView: RecyclerView
        lateinit var requestsAdapter: Requests_RecyclerViewAdapter

        requestsRecyclerView = findViewById(R.id.requestsRecyclerView1)
        requestsRecyclerView.layoutManager = LinearLayoutManager(this)
        requestsAdapter = Requests_RecyclerViewAdapter(ArrayList<RequestModel>(),this)
        requestsRecyclerView.adapter = requestsAdapter

        makeText(this, "Fetching data...", Toast.LENGTH_SHORT).show()

        db.collection("requests")
            .get()
            .addOnSuccessListener { result ->
                val requests = ArrayList<RequestModel>()
                for (document in result) {
                    val subject = document.getString("subject")
                    val startDate = document.getString("startDate")
                    val endDate = document.getString("endDate")
                    val request = RequestModel(subject, startDate, endDate)
                    requests.add(request)
                }
                requestsAdapter.dataSet = requests
                requestsAdapter.notifyDataSetChanged()
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