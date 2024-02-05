package com.example.projectmanager

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class Printer1MenuActivity : AppCompatActivity() {
    private val themes = listOf(R.style.ThemeBasic, R.style.ThemeFrog, R.style.ThemeValent, R.style.ThemeFall)
    private var currentThemeIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        // Retrieve the current theme index from SharedPreferences
        val sharedPreferences = getSharedPreferences("ThemePref", android.content.Context.MODE_PRIVATE)
        currentThemeIndex = sharedPreferences.getInt("themeIndex", 0)

        // Apply the current theme
        setTheme(themes[currentThemeIndex])
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