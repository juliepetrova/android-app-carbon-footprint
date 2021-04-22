package com.julie.reducecarbonfootprintapp

import android.os.Bundle
import androidx.appcompat.app.ActionBarDrawerToggle


class MainActivity : BaseActivity() {

    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mDrawerLayout = findViewById(R.id.drawer)
        mToggle = ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close)

        mDrawerLayout.addDrawerListener(mToggle)
        mToggle.syncState()
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)
        setNavigationViewListener()

//        var tv_dynamic = TextView(this)
//        tv_dynamic.textSize = 20f
//        tv_dynamic.text = "This is a dynamic TextView generated programmatically in Kotlin"
//
//        var box =  findViewById<LinearLayout>(R.id.box)
//        box.addView(tv_dynamic)
//
//        //Read all
//        Post().readAll().addOnSuccessListener { documents->
//            for (posts in documents){
//                var extractedPost = Gson().fromJson(JSONObject(posts.data).toString(), Post::class.java)
//                var tv = TextView(this)
//                tv.text = extractedPost.title+" "+extractedPost.content
//                box.addView(tv)
//            }
//        }
//
//        // Create
//        var Post1 = Post(4444,"ead","4444 Title","4444 Content",44,9,"08.04.21")
//        Post1.create()
//            .addOnSuccessListener { println("Successful") }
//           .addOnFailureListener { e -> println(e.localizedMessage) }
//
//        //Read one
//        Post().readOne("d").addOnSuccessListener { document ->
//            try {
//                if (document != null) {
//                    val extractedPost =
//                        Gson().fromJson(JSONObject(document.data).toString(), Post::class.java)
//                    println(extractedPost.parseJson())
//                } else {
//                    println("Not found")
//                }
//            } catch (ex: Exception) {
//                println(ex.localizedMessage)
//            }
//        }.addOnFailureListener { e ->
//            println(e.localizedMessage)
//        }
//
//        //Update
//        Post1.id=5555
//        Post1.userId="eadd"
//        Post().update("j8dzn6Y7CnxqNnV47FNj",Post1)
//            .addOnSuccessListener { println("Successfully updated") }
//            .addOnFailureListener { e -> println(e.localizedMessage) }
    }

}