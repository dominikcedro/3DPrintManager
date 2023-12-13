package com.example.projectmanager

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class PrinterChoiceActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_printer_choice)
        val printer1 = findViewById<Button>(R.id.Printer_1) // ultimaker 2+
        printer1.setOnClickListener {
            val intent = Intent(this@PrinterChoiceActivity, MenuActivity::class.java)
            startActivity(intent)
        }


    }
}