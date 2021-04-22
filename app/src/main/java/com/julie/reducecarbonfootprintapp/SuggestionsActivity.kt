package com.julie.reducecarbonfootprintapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ListView
import androidx.appcompat.app.ActionBarDrawerToggle
import com.google.android.material.tabs.TabLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.gson.Gson
import com.julie.reducecarbonfootprintapp.adapter.SuggestionListAdapter
import com.julie.reducecarbonfootprintapp.models.Suggestion
import com.julie.reducecarbonfootprintapp.models.User
import org.json.JSONObject




class SuggestionsActivity : BaseActivity() {

    val suggestions: MutableList<Suggestion> = ArrayList()

    private lateinit var listView: ListView
    private lateinit var layoutTab: TabLayout


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_suggestions)

        layoutTab = findViewById(R.id.layoutTab)


//      Navigation
        mDrawerLayout = findViewById(R.id.drawer)
        mToggle = ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close)

        mDrawerLayout.addDrawerListener(mToggle)
        mToggle.syncState()
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)
        setNavigationViewListener()
//      Navigation end


//        Read all suggestions
        Suggestion().readAll().addOnSuccessListener { documents ->
            for (dbsuggestion in documents) {

                var extractedSuggestion = Gson().fromJson(
                    JSONObject(dbsuggestion.data).toString(),
                    Suggestion::class.java
                )
                var result = extractedSuggestion as Suggestion
                result.documentId = dbsuggestion.reference.id
                suggestions.add(result)
            }


//            Display suggestions in view
            listView = findViewById(R.id.listView)

            createSuggestionsListAdapter(suggestions)

        }

//        Tab clicked
        layoutTab.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {

            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab?.text.toString() == "Suggestions") {
                    Log.d("Tab", "suggestions")

                    createSuggestionsListAdapter(suggestions)
                } else {
                    Log.d("tab", "completed")


                    User().readOne(FirebaseAuth.getInstance().getCurrentUser().getUid())
                        .addOnSuccessListener { documents ->

                            var extractedUser = Gson().fromJson(
                                JSONObject(documents.data).toString(),
                                User::class.java
                            )
                            var user = extractedUser as User
                            Log.d("User", user.firstName)
                            getCompletedSuggestions(user)
                        }

                }
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                // Handle tab reselect
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                // Handle tab unselect
            }
        })
    }

    fun createSuggestionsListAdapter(suggestions: MutableList<Suggestion>) {

        val myListAdapter = SuggestionListAdapter(this, suggestions)
        listView.adapter = myListAdapter

        listView.setOnItemClickListener() { adapterView, view, position, id ->
            val itemAtPos = adapterView.getItemAtPosition(position)
            var suggestion = itemAtPos as Suggestion

//          Open Master Details page
            val intent = Intent(this, SuggestionDetailsActivity::class.java)
            intent.putExtra("Suggestion", suggestion);
            startActivity(intent)

        }
    }

    fun getUser(): User {
        var user = User()
        User().readOne(FirebaseAuth.getInstance().getCurrentUser().getUid())
            .addOnSuccessListener { documents ->

                var extractedUser = Gson().fromJson(
                    JSONObject(documents.data).toString(),
                    User::class.java
                )
                user = extractedUser as User
            }

        return user
    }

    fun getCompletedSuggestions(user: User) {
        var completedSuggestions = mutableListOf<Suggestion>()

        for (suggestion in user.completedSuggestions) {
            Log.d("Suggestion list ", suggestion)
            Suggestion().readOne(suggestion)
                .addOnSuccessListener { documents ->

                    val extractedSuggestion = Gson().fromJson(
                        JSONObject(documents.data).toString(),
                        Suggestion::class.java
                    )

                    var result = extractedSuggestion as Suggestion
                    Log.d("Current suggestion ", result.title)
                    result.documentId = documents.reference.id
                    completedSuggestions.add(result)
                    createSuggestionsListAdapter(completedSuggestions)

                }
        }
        Log.d("all suggestions ", completedSuggestions.size.toString())

    }


    fun getSuggestion(suggestionId: String): Suggestion {
        var result = Suggestion()
        Log.d("Current suggestion ", "Entered function")

        Suggestion().readOne(suggestionId)
            .addOnSuccessListener { documents ->

                val extractedSuggestion = Gson().fromJson(
                    JSONObject(documents.data).toString(),
                    Suggestion::class.java
                )

                result = extractedSuggestion as Suggestion
                Log.d("Current suggestion ", result.title)
                result.documentId = documents.reference.id
            }
        return result

    }


}