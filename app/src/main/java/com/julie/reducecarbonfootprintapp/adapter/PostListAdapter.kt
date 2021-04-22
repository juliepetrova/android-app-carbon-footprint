package com.julie.reducecarbonfootprintapp.adapter

import android.app.Activity
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.julie.reducecarbonfootprintapp.R
import com.julie.reducecarbonfootprintapp.models.Post

class PostListAdapter(private val context: Activity, private val posts: MutableList<Post>) :
    ArrayAdapter<Post>(context, R.layout.post_component, posts) {


    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        val inflater = context.layoutInflater
        val rowView = inflater.inflate(R.layout.post_component, null, true)

        var upvoteCounter = 0
        var downvoteCounter = 0

        //Upvote functionality
        fun upvotePost(): Unit {
            println(posts[position].parseJson())
            Toast(context).showCustomToast("You liked this post", context)
            val updatedPostUpvotes = rowView.findViewById(R.id.upvotes) as TextView
            updatedPostUpvotes.text = (posts[position].upvotes + 1).toString()
            var updatedPost = posts[position]
            updatedPost.upvotes++
            Post().update(posts[position].documentId, updatedPost).addOnSuccessListener {
                println("Successfully updated!")
            }.addOnFailureListener {
                println("Error by update")
            }
        }

        val upvoteIcon = rowView.findViewById(R.id.like) as ImageView
        upvoteIcon.setOnClickListener {
            if (upvoteCounter == 0) {
                upvotePost()
                upvoteCounter++
            }
        }

        //End Upvote functionality


        //Downvote functionality
        fun downvotePost(): Unit {
            println(posts[position].parseJson())
            Toast(context).showCustomToast("You disliked this post", context)
            val updatedDownvotes = rowView.findViewById(R.id.downvotes) as TextView
            updatedDownvotes.text = (posts[position].downvotes + 1).toString()
            var updatedPost = posts[position]
            updatedPost.downvotes++
            Post().update(posts[position].documentId, updatedPost).addOnSuccessListener {
                println("Successfully updated!")
            }.addOnFailureListener {
                println("Error by update")
            }
        }

        val downvoteIcon = rowView.findViewById(R.id.dislike) as ImageView
        downvoteIcon.setOnClickListener {
            if (downvoteCounter == 0) {
                downvotePost();
                downvoteCounter++;
            }
        }
        //End Downvote functionality

        val title = rowView.findViewById(R.id.title) as TextView
        val content = rowView.findViewById(R.id.post) as TextView
        val upvotes = rowView.findViewById(R.id.upvotes) as TextView
        val downvotes = rowView.findViewById(R.id.downvotes) as TextView

        content.text = posts[position].content
        title.text = posts[position].title
        upvotes.text = posts[position].upvotes.toString()
        downvotes.text = posts[position].downvotes.toString()

//        titleText.text = suggestions[position].title
//        points.text= "+ " + suggestions[position].points.toString() + "p"
//        tons.text = "Reduce up to " + suggestions[position].savedTonsPerYear.toString() + " tons of CO2 per year"

        return rowView
    }

    fun Toast.showCustomToast(message: String, activity: Activity)
    {
        val layout = activity.layoutInflater.inflate (
            R.layout.custom_toast_layout,
            activity.findViewById(R.id.toast_container)
        )

        // set the text of the TextView of the message
        val textView = layout.findViewById<TextView>(R.id.toast_text)
        textView.text = message

        // use the application extension function
        this.apply {
            setGravity(Gravity.BOTTOM, 0, 40)
            duration = Toast.LENGTH_LONG
            view = layout
            show()
        }
    }
}