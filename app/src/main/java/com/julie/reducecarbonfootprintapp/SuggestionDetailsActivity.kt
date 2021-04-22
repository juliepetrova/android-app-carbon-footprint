package com.julie.reducecarbonfootprintapp

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import com.google.firebase.auth.FirebaseAuth
import com.google.gson.Gson
import com.julie.reducecarbonfootprintapp.models.Suggestion
import com.julie.reducecarbonfootprintapp.models.User
import org.json.JSONObject

class SuggestionDetailsActivity : BaseActivity() {

    private lateinit var titleTv: TextView
    private lateinit var tonsTv: TextView
    private lateinit var descriptionTv: TextView
    private lateinit var peopleTv: TextView
    private lateinit var progressPb: ProgressBar
    private lateinit var joinNowBtn: Button
    private lateinit var suggestion: Suggestion
    private lateinit var signedInUser: User
    private lateinit var userUid: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_suggestion_details)

        //        Navigation
        mDrawerLayout = findViewById(R.id.drawer)
        mToggle = ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close)

        mDrawerLayout.addDrawerListener(mToggle)
        mToggle.syncState()
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)
        setNavigationViewListener()
//        Navigation end

        titleTv = findViewById(R.id.title)
        tonsTv = findViewById(R.id.tons)
        descriptionTv = findViewById(R.id.description)
        peopleTv = findViewById(R.id.people)
        progressPb = findViewById(R.id.progressBar)
        joinNowBtn = findViewById(R.id.joinNowBtn)

        signedInUser = User()

        suggestion = intent.getSerializableExtra("Suggestion") as Suggestion
        titleTv.text = suggestion.title
        tonsTv.text = suggestion.savedTonsPerYear.toString() + "tons \nper year"
        descriptionTv.text = suggestion.content
        peopleTv.text = "512/1000 people"
        progressPb.progress = 51


        userUid = FirebaseAuth.getInstance().getCurrentUser().getUid()
        User().readOne(userUid)
            .addOnSuccessListener { documents ->

                var extractedUser = Gson().fromJson(
                    JSONObject(documents.data).toString(),
                    User::class.java
                )
                signedInUser = extractedUser as User

                if (signedInUser.completedSuggestions.contains(suggestion.documentId)) {
                    joinNowBtn.text = "Unsubscribe"
                }
            }



        joinNowBtn.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
                addToUserProfile()
            }

        })
    }


    fun addToUserProfile() {

        if (!signedInUser.completedSuggestions.contains(suggestion.documentId)) {
            signedInUser.completedSuggestions.add(suggestion.documentId)
            signedInUser.points += suggestion.points

            User().update(userUid, signedInUser).addOnSuccessListener {
                val intent = Intent(this, RewardsActivity::class.java)
                intent.putExtra("Suggestion", suggestion);
                intent.putExtra("User", signedInUser);
                startActivity(intent)
            }
        } else {
            Log.d("Show alert", "Alert user is subscribed")
            showAlert()
        }

    }

    fun showAlert() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("You are already participating in this challenge")
        builder.setMessage("Are you sure you want to unsubscribe?")
        builder.setIcon(R.drawable.ic_launcher_background)

        builder.setPositiveButton("Yes", DialogInterface.OnClickListener { dialog, which ->
            dialog.dismiss()
            unsubscribeFromChallenge()
        })

        builder.setNegativeButton("No", DialogInterface.OnClickListener { dialog, which ->
            dialog.dismiss()
        })
        builder.show()

    }


    fun unsubscribeFromChallenge() {

        signedInUser.completedSuggestions.remove(suggestion.documentId)
        User().update(userUid, signedInUser).addOnSuccessListener {
            Toast.makeText(this, "You have successfully unsubscribed", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

}