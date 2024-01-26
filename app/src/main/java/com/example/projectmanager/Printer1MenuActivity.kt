package com.example.projectmanager

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class Printer1MenuActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_printer1_menu)

        val reportsButton = findViewById<Button>(R.id.checkReportsButton) // check reports button
        val requestsCheckButton = findViewById<Button>(R.id.checkRequestButton) // check requests button
        val requestsCreateButton = findViewById<Button>(R.id.createRequestButton) // create new request button
        val goBackButton = findViewById<Button>(R.id.goPrinterChoiceButton) // button to go back to printer choice

        // when goBackButton clicked go back to printer choice
        goBackButton.setOnClickListener{
            val intent1 = Intent(this@Printer1MenuActivity, PrinterChoiceActivity::class.java)
            startActivity(intent1)
            finish()
        }

        // when reportsButton clicked go to reports actiivt
        reportsButton.setOnClickListener{
            val intent2 = Intent(this@Printer1MenuActivity, Printer1Reports::class.java)
            startActivity(intent2)
            finish()
        }

        requestsCheckButton.setOnClickListener{
            val intent3 = Intent(this@Printer1MenuActivity, Printer1Requests::class.java)
            startActivity(intent3)
            finish()
        }
        requestsCreateButton.setOnClickListener{
            val intent4 = Intent(this@Printer1MenuActivity, CreateNewRequest::class.java)
            startActivity(intent4)
            finish()
        }

    }
}