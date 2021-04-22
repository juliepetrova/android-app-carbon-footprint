package com.julie.reducecarbonfootprintapp

import android.os.Bundle
import android.util.Log
import android.widget.ListView
import androidx.appcompat.app.ActionBarDrawerToggle
import com.google.android.material.tabs.TabLayout
import com.google.gson.Gson
import com.julie.reducecarbonfootprintapp.adapter.UserRankingAdapter
import com.julie.reducecarbonfootprintapp.models.Suggestion
import com.julie.reducecarbonfootprintapp.models.User
import org.json.JSONObject
import kotlin.collections.ArrayList


class LeaderboardActivity : BaseActivity() {

    private val users: MutableList<User> = ArrayList()


    private lateinit var listView: ListView



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_leaderboard)

//        Navigation
        mDrawerLayout = findViewById(R.id.drawer)
        mToggle = ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close)

        mDrawerLayout.addDrawerListener(mToggle)
        mToggle.syncState()
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)
        setNavigationViewListener()
//        Navigation end


//        Read all suggestions
        User().readAll().addOnSuccessListener { documents ->
            Log.d("Read all suggestions", "Success")
            for (user in documents) {
                var extractedUser = Gson().fromJson(
                    JSONObject(user.data).toString(),
                    User::class.java
                )
                var result = extractedUser as User
                Log.d("Read all users", result.firstName)
                users.add(result)
                Log.d("Read all users", users.size.toString())
            }
            listView = findViewById(R.id.listView)
            createUserRankingAdapter(users)
        }
    }

    private fun createUserRankingAdapter(users: MutableList<User>) {

        val myListAdapter = UserRankingAdapter(this, users)
        listView.adapter = myListAdapter
        Log.d("Fill in list", "List filled")

        listView.setOnItemClickListener() { adapterView, view, position, id ->
            val itemAtPos = adapterView.getItemAtPosition(position)
            var suggestion = itemAtPos as Suggestion
            val itemIdAtPos = adapterView.getItemIdAtPosition(position)


        }
    }
}

