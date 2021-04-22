package com.julie.reducecarbonfootprintapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import com.google.gson.Gson
import com.julie.reducecarbonfootprintapp.adapter.GoalListAdapter
import com.julie.reducecarbonfootprintapp.models.Goal
import org.json.JSONObject

class GoalsActivity : BaseActivity() {
    private lateinit var listViewGoals: ListView
    val goals: MutableList<Goal> = ArrayList()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_goals)

//        Navigation
        mDrawerLayout = findViewById(R.id.drawer)
        mToggle = ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close)

        mDrawerLayout.addDrawerListener(mToggle)
        mToggle.syncState()
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)
        setNavigationViewListener()
        //        Navigation end

        //        Get goals from database
        readAllGoals()


    }


    fun createGoalsListAdapter(goals: MutableList<Goal>) {

        val myListAdapter = GoalListAdapter(this, goals)
        listViewGoals.adapter = myListAdapter

        listViewGoals.setOnItemClickListener() { adapterView, view, position, id ->
            val itemAtPos = adapterView.getItemAtPosition(position)
            var goal = itemAtPos as Goal
            val itemIdAtPos = adapterView.getItemIdAtPosition(position)
            Toast.makeText(
                applicationContext,
                "this is the position" + itemIdAtPos,
                Toast.LENGTH_SHORT
            )


//          Open Master Details page
            val intent = Intent(this, GoalDetailsActivity::class.java)
            intent.putExtra("Goal", goal);
            startActivity(intent)

        }
    }

    fun readAllGoals() {
        Goal().readAll().addOnSuccessListener { documents ->
            for (dbgoals in documents) {
                var extractedGoal = Gson().fromJson(
                    JSONObject(dbgoals.data).toString(),
                    Goal::class.java
                )
                var result = extractedGoal as Goal
                Log.d("Goal title", result.title)
                goals.add(result)
            }
            //            Display goals in view
            listViewGoals = findViewById(R.id.listViewGoals)

            createGoalsListAdapter(goals)
        }

    }
}