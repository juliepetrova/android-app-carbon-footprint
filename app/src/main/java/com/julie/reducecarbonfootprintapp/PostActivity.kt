package com.julie.reducecarbonfootprintapp

import android.os.Bundle
import android.util.Log
import android.widget.ListView
import androidx.appcompat.app.ActionBarDrawerToggle
import com.google.gson.Gson
import com.julie.reducecarbonfootprintapp.adapter.PostListAdapter
import com.julie.reducecarbonfootprintapp.models.Post
import org.json.JSONObject

class PostActivity : BaseActivity() {

    val posts: MutableList<Post> = ArrayList()
    val emptyArray: MutableList<Post> = ArrayList()

    private lateinit var listView: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post)

//        var Post1 = Post()
//        Post1.title="Reduce your carbon footprint"
//        Post1.userId ="users/1f9H59zxBWVgMBVSJ5VBU9FvAvn2"
//        Post1.content = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."
//        Post1.upvotes =10
//        Post1.downvotes = 2
//        Post1.createdAt = "14.04.2021"
//        Post1.create()
//            .addOnSuccessListener { println("Successful") }
//            .addOnFailureListener { e -> println(e.localizedMessage+" "+"Unsuccessful") }



//        Navigation
        mDrawerLayout = findViewById(R.id.drawer)
        mToggle = ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close)

        mDrawerLayout.addDrawerListener(mToggle)
        mToggle.syncState()
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)
        setNavigationViewListener()
//        Navigation end


        Post().readAll().addOnSuccessListener { documents ->
            for (document in documents) {
                val extractedPost =
                    Gson().fromJson(JSONObject(document.data).toString(), Post::class.java)
                var result = extractedPost as Post
                result.documentId = document.reference.id
                Log.d("Read all posts", result.title)
                posts.add(result)
            }
            listView = findViewById(R.id.listView)
            createPostListAdapter(posts)
        }
            .addOnFailureListener { exception ->
                println(exception.localizedMessage)
            }


    }

    private fun createPostListAdapter(posts: MutableList<Post>) {
        val myListAdapter = PostListAdapter(this, posts)
        listView.adapter = myListAdapter
        Log.d("Fill in list of Posts", "List filled")


    }

}