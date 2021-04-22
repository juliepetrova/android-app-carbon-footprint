package com.julie.reducecarbonfootprintapp

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth

class LogInActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    private lateinit var emailEt: EditText
    private lateinit var passwordEt: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in)

        auth = FirebaseAuth.getInstance()

        emailEt = findViewById(R.id.editTextEmail)
        passwordEt = findViewById(R.id.editTextPassword)

    }

    fun onRegisterClick(view: View) {
        val intent = Intent(this, RegistrationActivity::class.java)
        startActivity(intent)
        finish()
    }

    fun onLogInClick(view: View) {
        var email: String = emailEt.text.toString()
        var password: String = passwordEt.text.toString()

        if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            Toast.makeText(this@LogInActivity, "Please fill all the fields", Toast.LENGTH_LONG).show()
        } else{
            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, OnCompleteListener { task ->
                if(task.isSuccessful) {
                    Toast.makeText(this, "Successfully Logged In", Toast.LENGTH_LONG).show()
                    val intent = Intent(this, SuggestionsActivity::class.java)
                    startActivity(intent)
                    finish()
                }else {
                    Toast.makeText(this, "Login Failed", Toast.LENGTH_LONG).show()
                }
            })
        }
    }
}