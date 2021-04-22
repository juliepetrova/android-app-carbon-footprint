package com.julie.reducecarbonfootprintapp

import android.content.Context
import android.os.*
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.julie.reducecarbonfootprintapp.models.Suggestion
import com.julie.reducecarbonfootprintapp.models.User

class RewardsActivity : AppCompatActivity() {

    private lateinit var newPointsTv : TextView
    private lateinit var userPointsTv: TextView

    private lateinit var suggestion: Suggestion
    private lateinit var user: User


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rewards)

        newPointsTv = findViewById(R.id.tvNewPoints)
        userPointsTv = findViewById(R.id.tvUserPoints)

        suggestion = intent.getSerializableExtra("Suggestion") as Suggestion
        user = intent.getSerializableExtra("User") as User

        newPointsTv.text = suggestion.points.toString() + "POINTS"
        var totalPoints = user.points
        userPointsTv.text = "(TOTAL:" + totalPoints + "POINTS)"

        val vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        if (vibrator.hasVibrator()) { // Vibrator availability checking
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrator.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE)) // New vibrate method for API Level 26 or higher
            } else {
                vibrator.vibrate(500) // Vibrate method for below API Level 26
            }
        }
        

        Handler().postDelayed({
            finish()
        }, 5000)

    }

    fun closeIntent(view: View) {
        finish()
    }
}