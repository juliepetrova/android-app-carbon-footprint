package com.julie.reducecarbonfootprintapp

import android.os.Bundle
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import com.julie.reducecarbonfootprintapp.models.Goal

class GoalDetailsActivity : BaseActivity() {

    private lateinit var titleTv: TextView
    private lateinit var startDateTv: TextView
    private lateinit var descriptionTv: TextView
    private lateinit var endDateTv: TextView
    private lateinit var participantsTv: TextView
    private lateinit var progressPb: ProgressBar
    private lateinit var joinNowBtn: Button

    private lateinit var goal: Goal


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_goal_details_page)


//        Navigation
        mDrawerLayout = findViewById(R.id.drawer)
        mToggle = ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close)

        mDrawerLayout.addDrawerListener(mToggle)
        mToggle.syncState()
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)
        setNavigationViewListener()
//        Navigation end


        titleTv = findViewById(R.id.tvTitle)
        startDateTv = findViewById(R.id.tvDateStart)
        endDateTv = findViewById(R.id.tvDateEnd)
        descriptionTv = findViewById(R.id.tvDescription)
        participantsTv = findViewById(R.id.tvPeople)
        progressPb = findViewById(R.id.progressBar)
        joinNowBtn = findViewById(R.id.joinNowBtn)

        getGoal()
//        joinNowBtn.setOnClickListener(addToUserProfile)

    }

    fun getGoal() {
        goal = intent.getSerializableExtra("Goal") as Goal
        if (goal != null) {

            titleTv.text = "#" + goal.title
            startDateTv.text = goal.startDate
            endDateTv.text = goal.endDate
            descriptionTv.text = goal.description
            participantsTv.text = "" + goal.participants + "/" + goal.goalPeople + " people"
            progressPb.progress = ((goal.participants * 100 ) / goal.goalPeople).toInt()
        }

    }
}