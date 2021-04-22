package com.julie.reducecarbonfootprintapp.adapter

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ProgressBar
import android.widget.TextView
import com.julie.reducecarbonfootprintapp.R
import com.julie.reducecarbonfootprintapp.models.Goal

class GoalListAdapter (private val context: Activity, private val goals: MutableList<Goal>)
    : ArrayAdapter<Goal>(context, R.layout.goal_component, goals) {

    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        val inflater = context.layoutInflater
        val rowView = inflater.inflate(R.layout.goal_component, null, true)

        val titleChalengeText = rowView.findViewById(R.id.tvTitleChallenge) as TextView
        val description = rowView.findViewById(R.id.tvDescription) as TextView
        val participants = rowView.findViewById(R.id.tvPeople) as TextView
        val progressBar = rowView.findViewById(R.id.progressBar) as ProgressBar


        titleChalengeText.text = "#"+ goals[position].title
        description.text= goals[position].description.toString()
        participants.text =goals[position].participants.toString() + "/" + goals[position].goalPeople.toString() + " people"
        progressBar.progress = ((goals[position].participants * 100) / goals[position].goalPeople).toInt()

        return rowView
    }
}