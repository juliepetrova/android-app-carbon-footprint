package com.julie.reducecarbonfootprintapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth

open class BaseActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    protected lateinit var mDrawerLayout: DrawerLayout
    protected lateinit var mToggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mDrawerLayout = findViewById(R.id.drawer)
        mToggle = ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close)

        mDrawerLayout.addDrawerListener(mToggle)
        mToggle.syncState()
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)
        setNavigationViewListener()
    }

    protected open fun setNavigationViewListener() {
        val navigationView = findViewById<View>(R.id.nav_view) as NavigationView
        navigationView.setNavigationItemSelectedListener(this)
    }
    // --- Navigation Bar Action
    override fun onBackPressed() {
        val drawer = findViewById<View>(R.id.drawer) as DrawerLayout
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(mToggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        Log.d("Navigation pressed",  R.id.suggestion.toString() )

        val id: Int = item.itemId

        if (id == R.id.suggestion) {
            val intent = Intent(this, SuggestionsActivity::class.java)
            startActivity(intent)
            finish()
        } else if (id == R.id.posts) {
            val intent = Intent(this, PostActivity::class.java)
            startActivity(intent)
            finish()
        } else if (id == R.id.leaderoard) {
            val intent = Intent(this,LeaderboardActivity::class.java)
            startActivity(intent)
            finish()
        } else if (id == R.id.profile) {
            val intent = Intent(this, StatisticsActivity::class.java)
            startActivity(intent)
            finish()
        } else if (id == R.id.goals) {
            val intent = Intent(this, GoalsActivity::class.java)
            startActivity(intent)
            finish()
        } else if (id == R.id.logout) {
            FirebaseAuth.getInstance().signOut();
            Log.d("Auth", "User is signed out")
            val intent = Intent(this, LogInActivity::class.java)
            startActivity(intent)
            finish()
        }
        val drawer = findViewById<View>(R.id.drawer) as DrawerLayout
        drawer.closeDrawer(GravityCompat.START)
        return true
    }
}