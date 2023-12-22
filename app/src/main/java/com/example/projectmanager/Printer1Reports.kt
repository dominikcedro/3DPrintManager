package com.example.projectmanager

import android.annotation.SuppressLint
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projectmanager.CommentsRecycler.CommentModel
import com.example.projectmanager.CommentsRecycler.Comments_RecyclerViewAdapter
import com.example.projectmanager.Firestore.CommentData
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class Printer1Reports : AppCompatActivity() {
    val db = Firebase.firestore

    @SuppressLint("NewApi", "NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_printer1_reports)
        lateinit var commentsRecyclerView: RecyclerView
        lateinit var commentsAdapter: Comments_RecyclerViewAdapter

        commentsRecyclerView = findViewById(R.id.recyclerView1)
        commentsRecyclerView.layoutManager = LinearLayoutManager(this)
        commentsAdapter = Comments_RecyclerViewAdapter(ArrayList<CommentModel>())
        commentsRecyclerView.adapter = commentsAdapter


        Toast.makeText(this, "Fetching data...", Toast.LENGTH_SHORT).show()

        // get comments from firestore
        db.collection("comments")
            .get()
            .addOnSuccessListener { result ->
//                Toast.makeText(this, "Data fetched successfully", Toast.LENGTH_SHORT).show()
                val comments = ArrayList<CommentModel>()
                for (document in result) {
                    val printer = document.getString("printer")
                    val date = document.getString("date")
                    val name = document.getString("username")
                    val mail = document.getString("email")
                    val comment = document.getString("comment")
                    val likes = document.getLong("likes")?.toInt()
                    val commentModel = CommentModel(printer, date, name, mail, comment, likes)
                    comments.add(commentModel)
                }
                comments.sortWith(compareBy { it.date })
                commentsAdapter.dataSet = comments
                commentsAdapter.notifyDataSetChanged()


            }


        // to creae comment using the comment button
        val commentText = findViewById<EditText>(R.id.editTextComment)
        val commentButton = findViewById<Button>(R.id.postTheComment)
        commentButton.isEnabled = false
        // if commentText is empty disable button
        commentText.addTextChangedListener {
            commentButton.isEnabled = it.toString().isNotEmpty()
        }
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
            }

        // post comment, add to firestore comments collection
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
            db.collection("comments")
                .add(commentData)
            db.collection("comments")
                .get()
                .addOnSuccessListener { result ->
//                Toast.makeText(this, "Data fetched successfully", Toast.LENGTH_SHORT).show()
                    val comments = ArrayList<CommentModel>()
                    for (document in result) {
                        val printer = document.getString("printer")
                        val date = document.getString("date")
                        val name = document.getString("username")
                        val mail = document.getString("email")
                        val comment = document.getString("comment")
                        val likes = document.getLong("likes")?.toInt()
                        val commentModel = CommentModel(printer, date, name, mail, comment, likes)
                        comments.add(commentModel)
                    }
                    comments.sortWith(compareBy { it.date })
                    commentsAdapter.dataSet = comments
                    commentsAdapter.notifyDataSetChanged()


                }
            commentText.text.clear()

            }
        // create array of comments type is CommentModel

        // function to set
}}