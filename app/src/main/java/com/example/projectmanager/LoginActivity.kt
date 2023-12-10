package com.example.projectmanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ClickableSpan
import android.view.View
import android.widget.TextView

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val userEmail = findViewById<EditText>(R.id.enter_email_login)
        val userPassword = findViewById<EditText>(R.id.enter_password_login)

        val contactCreator = findViewById<TextView>(R.id.contact_me_text)
        val fullText = "If you have any questions, contact me"
        val clickableWord = "contact me"
        val spannableString = SpannableString(fullText)
        val startIndex = fullText.indexOf(clickableWord)
        // clicable substring for contacting me
        val clickableSpan = object : ClickableSpan(){
            override fun onClick(widget: View) {
                // here put activity to send me email
            }
        }
    }
}