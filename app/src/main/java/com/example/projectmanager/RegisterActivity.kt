package com.example.projectmanager

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Switch
import android.widget.Toast
import com.example.projectmanager.Firestore.FireStoreClass
import com.example.projectmanager.Firestore.FireStoreData
import com.example.projectmanager.Firestore.User
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.Firebase
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import java.lang.reflect.Array.set

class RegisterActivity : AppCompatActivity() {
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
            val name = userName.text.toString()
            val email = userEmail.text.toString()
            val password = userPassword2.text.toString()
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(
                    OnCompleteListener<AuthResult> { task ->
                        if (task.isSuccessful) {
                            val firebaseUser: FirebaseUser = task.result!!.user!!
//                            Toast.makeText(
//                                this@RegisterActivity,
//                                "You are registered successfully",
//                                Toast.LENGTH_SHORT
//                            ).show()

                            val user = User(
                                name,
                                email
                            )
                            FireStoreClass().registerUserFS(this@RegisterActivity, user)
                            db.collection("usersData")
                                .document(Firebase.auth.currentUser!!.uid)
                                .set(user)
                                .addOnSuccessListener {
                                    Toast.makeText(this, "User added successfully", Toast.LENGTH_SHORT).show()
                                }
                            val intent =
                                Intent(this@RegisterActivity, LoginActivity::class.java)
                            startActivity(intent)
                            finish()
                        } else {
                            Toast.makeText(
                                this@RegisterActivity,
                                task.exception!!.message.toString(),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                )
        }



    }
}