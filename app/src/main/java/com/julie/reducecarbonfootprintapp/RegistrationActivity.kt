package com.julie.reducecarbonfootprintapp

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.julie.reducecarbonfootprintapp.models.FirebaseConnection


class RegistrationActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var emailEt: EditText
    private lateinit var passwordEt: EditText
    private lateinit var nameEt: EditText
    private lateinit var cityEt: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)
        mAuth = FirebaseAuth.getInstance();
        emailEt = findViewById(R.id.editTextEmail)
        passwordEt = findViewById(R.id.editTextPassword)
        nameEt = findViewById(R.id.editTextName)
        cityEt = findViewById(R.id.editTextCity)

        println(mAuth.toString());

        // Configure Google Sign In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)
    }

    override fun onStart() {
        super.onStart()

        if (mAuth.getCurrentUser() != null) {
            // handle already created user
        }
    }

    fun onLoginClick(view: View) {
        val intent = Intent(this, LogInActivity::class.java)
        startActivity(intent)
        finish()
    }

    fun onRegistrationClick(view: View) {
        var email: String = emailEt.text.toString()
        var password: String = passwordEt.text.toString()

        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_LONG).show()
        } else {
            mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, OnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "Successfully Registered", Toast.LENGTH_LONG).show()

//                    Save additional data

                        val strs = nameEt.text.toString().split(" ").toMutableList()
                        if(strs.size == 1) {
                            strs.add(" ")
                        }
                        val newUser = hashMapOf(
                            "city" to cityEt.text.toString(),
                            "firstName" to strs[0],
                            "lastName" to strs[1],
                            "points" to 0,
                            "completedSugeestions" to arrayListOf(null),
                            "upvotedPostIds" to arrayListOf(null),
                        )
                        Log.d(
                            "Created user uid",
                            FirebaseAuth.getInstance().getCurrentUser().getUid()
                        )

                        FirebaseConnection().db.collection("users")
                            .document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .set(newUser)
                            .addOnSuccessListener {
                                Log.d(TAG, "DocumentSnapshot successfully written!")
                                val intent = Intent(this, SuggestionsActivity::class.java)
                                startActivity(intent)
                                finish()
                            }
                            .addOnFailureListener { e -> Log.w(TAG, "Error writing document", e) }

                    } else {
                        Toast.makeText(this, "Registration Failed", Toast.LENGTH_LONG).show()
                    }
                })
        }
    }

    // [START onactivityresult]
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)!!
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.id)
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e)
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        mAuth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithCredential:success")
                    val user = mAuth.currentUser
                    val intent = Intent(this, SuggestionsActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                }
            }
    }
    // [END auth_with_google]

    // [START signin]
    fun onSignInGoogleClick(view: View) {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    companion object {
        private const val TAG = "GoogleActivity"
        private const val RC_SIGN_IN = 9001
    }
}