package com.example.projectmanager

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.text.Spannable
import android.text.SpannableString
import android.text.TextUtils
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val userEmail = findViewById<EditText>(R.id.enter_email_login)
        val userPassword = findViewById<EditText>(R.id.enter_password_login)

        // contact creator spannable string at the bottom
        val contactCreator = findViewById<TextView>(R.id.contact_me_text)
        val fullText = "If you have any questions, contact me"
        val clickableWord = "contact me"
        val spannableString = SpannableString(fullText)
        val startIndex = fullText.indexOf(clickableWord)
        // clicable substring for contacting me
        val clickableSpan = object : ClickableSpan(){
            override fun onClick(widget: View) {
                val intent0 = Intent(this@LoginActivity, HelpActivity::class.java)
                startActivity(intent0)
            }
        }
        spannableString.setSpan(clickableSpan, startIndex, startIndex + clickableWord.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        contactCreator.text = spannableString
        contactCreator.movementMethod = LinkMovementMethod.getInstance()


        val loginButton: Button = findViewById<Button>(R.id.login_button)
        loginButton.setOnClickListener {
            val intent = Intent(this@LoginActivity, Printer1MenuActivity::class.java)
            when{
                TextUtils.isEmpty(userEmail.text.toString().trim{it <= ' '}) -> {
                    Toast.makeText(
                        this@LoginActivity,
                        "Please enter email.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                TextUtils.isEmpty(userPassword.text.toString().trim{it <= ' '}) -> {
                    Toast.makeText(
                        this@LoginActivity,
                        "Please enter password.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                else -> {
                    val email: String = userEmail.text.toString().trim{it <= ' '}
                    val password: String = userPassword.text.toString().trim{it <= ' '}
                    FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                val firebaseUser: FirebaseUser = task.result!!.user!!
                                Toast.makeText(
                                    this@LoginActivity,
                                    "You are logged in successfully",
                                    Toast.LENGTH_SHORT
                                ).show()

                                val user = hashMapOf(
                                    "password" to password
                                )
                                val intent1 = Intent(this@LoginActivity, PrinterChoiceActivity::class.java)
                                startActivity(intent1)
                                finish()
                            }
                            else {
                                Toast.makeText(
                                    this@LoginActivity,
                                    task.exception!!.message.toString(),
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                }}}}}