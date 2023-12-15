package com.example.projectmanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Switch
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        val userName = findViewById<EditText>(R.id.editTextName)
        val userEmail = findViewById<EditText>(R.id.editTextEmail)
        val userPassword1 = findViewById<EditText>(R.id.editTextPassword1)
        val userPassword2 = findViewById<EditText>(R.id.editTextPassword2)
        val switch = findViewById<Switch>(R.id.switchGoodProgram)
        val buttonRegister = findViewById<Button>(R.id.registerButton)

        // check if passwords match
        userPassword2.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                if (userPassword1.text.toString() != userPassword2.text.toString()) {
                    userPassword2.error = "Passwords do not match"
                }
            }
        }

        // check if email is valid
        userEmail.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                if (!userEmail.text.toString().contains("@")) {
                    userEmail.error = "Invalid email"
                }
            }
        }

        // check if name is valid
        userName.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                if (userName.text.toString().length < 3) {
                    userName.error = "Name must be at least 3 characters long"
                }
            }
        }

        //very impractical joke :)
        buttonRegister.isEnabled = false
        switch.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                buttonRegister.isEnabled = true
            } else {
                buttonRegister.isEnabled = false
            }
        }

        // when register button clicked, register user
        buttonRegister.setOnClickListener{
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(userEmail.text.toString(), userPassword1.text.toString())
                .addOnCompleteListener{
                    if (it.isSuccessful) {
                        val user = FirebaseAuth.getInstance().currentUser
                        user?.sendEmailVerification()
                            ?.addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    // Sign in success, update UI with the signed-in user's information
                                    // toast message that it was successful
                                    Toast.makeText(
                                        this@RegisterActivity,
                                        "You are registered successfully",
                                        Toast.LENGTH_SHORT).show()
                                }
                            }
                        finish()
                    } else {
                        Toast.makeText(
                            this@RegisterActivity,
                            "Registration failed",
                            Toast.LENGTH_SHORT).show()
                    }
                }
        }



    }
}