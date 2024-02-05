package com.example.projectmanager

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Spannable
import android.text.SpannableString
import android.text.TextUtils
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class LoginActivity : AppCompatActivity() {
    private var lastClickTime: Long = 0
    private var clickCount: Int = 0
    private val themes = listOf(R.style.Theme1, R.style.ThemeFrog, R.style.ThemeValent, R.style.ThemeFall)
    private var currentThemeIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        // Retrieve the current theme index from SharedPreferences
        val sharedPreferences = getSharedPreferences("ThemePref", Context.MODE_PRIVATE)
        currentThemeIndex = sharedPreferences.getInt("themeIndex", 0)

        // Apply the current theme
        setTheme(themes[currentThemeIndex])

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val userEmail = findViewById<EditText>(R.id.enter_email_login)
        val userPassword = findViewById<EditText>(R.id.enter_password_login)

        // contact creator spannable string at the bottom
        val contactCreator = findViewById<TextView>(R.id.contact_me_text)
        val fullText = "Need help? Contact me."
        val clickableWord = "Contact me."
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

        // go to register acitivty spannable string
        val goRegister = findViewById<TextView>(R.id.tvGoRegister)
        val fullTextGoRegister = "Not a user? Register here"
        val clickableWordGoRegister = "Register here"
        val spannableStringGoRegister = SpannableString(fullTextGoRegister)
        val startIndexGoRegister = fullTextGoRegister.indexOf(clickableWordGoRegister)
        // clicable substring for going into reigsiter activity
        val clickableSpanGoRegister = object : ClickableSpan(){
            override fun onClick(widget: View) {
                val intent5 = Intent(this@LoginActivity, RegisterActivity::class.java)
                startActivity(intent5)
            }
        }
        spannableStringGoRegister.setSpan(clickableSpanGoRegister, startIndexGoRegister, startIndexGoRegister + clickableWordGoRegister.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        goRegister.text = spannableStringGoRegister
        goRegister.movementMethod = LinkMovementMethod.getInstance()

        val loginButton: Button = findViewById<Button>(R.id.login_button)
        loginButton.setOnClickListener {
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
                }
            }
        }

        val logoButton: Button = findViewById(R.id.logo_button)
        logoButton.setOnClickListener {
            val currentTime = System.currentTimeMillis()
            if (currentTime - lastClickTime < 1000) {
                // Double click detected
                clickCount = 0

                // Increment the theme index, wrapping back to 0 if it's past the end of the list
                currentThemeIndex = (currentThemeIndex + 1) % themes.size

                // Save the current theme index in SharedPreferences
                val editor = sharedPreferences.edit()
                editor.putInt("themeIndex", currentThemeIndex)
                editor.apply()

                // Restart the activity to apply the new theme
                recreate()
            } else {
                // Single click detected, start a delay
                clickCount++
                Handler(Looper.getMainLooper()).postDelayed({
                    if (clickCount == 1) {
                        // Single click confirmed, show toast
                        Toast.makeText(this, "Push the button twice to change the theme", Toast.LENGTH_SHORT).show()
                        clickCount = 0
                    }
                }, 1000)
            }
            lastClickTime = currentTime
        }
    }
}