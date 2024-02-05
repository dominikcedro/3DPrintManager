package com.example.projectmanager

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.widget.Button
import android.widget.TextView
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore
import io.grpc.Context

class PrinterChoiceActivity : AppCompatActivity() {
    val db = Firebase.firestore
    private val themes = listOf(R.style.ThemeBasic, R.style.ThemeFrog, R.style.ThemeValent, R.style.ThemeFall)
    private var currentThemeIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        // Retrieve the current theme index from SharedPreferences
        val sharedPreferences = getSharedPreferences("ThemePref", android.content.Context.MODE_PRIVATE)
        currentThemeIndex = sharedPreferences.getInt("themeIndex", 0)

        // Apply the current theme
        setTheme(themes[currentThemeIndex])
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_printer_choice)

        val auth = FirebaseAuth.getInstance()
        val userId = auth.currentUser?.uid

        val helloUser = findViewById<TextView>(R.id.helloUser)

        if (userId != null) {
            db.collection("usersData").document(userId)
                .get()
                .addOnSuccessListener { document ->
                    if (document != null) {
                        val userName = document.getString("name")
                        if (userName != null) {
                            val boldUserName = "<b>$userName</b>"
                            helloUser.text = Html.fromHtml("Hello, $boldUserName", Html.FROM_HTML_MODE_LEGACY)
                        }
                    } else {
                        Log.d(TAG, "No such document")
                    }
                }
                .addOnFailureListener { exception ->
                    Log.d(TAG, "get failed with ", exception)
                }
        }
        val printer1 = findViewById<Button>(R.id.Printer_1) // ultimaker 2+
        printer1.setOnClickListener {
            val intent = Intent(this@PrinterChoiceActivity, Printer1MenuActivity::class.java)
            startActivity(intent)
        }


    }
}