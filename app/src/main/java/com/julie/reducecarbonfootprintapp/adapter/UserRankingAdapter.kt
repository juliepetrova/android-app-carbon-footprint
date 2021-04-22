package com.julie.reducecarbonfootprintapp.adapter

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.julie.reducecarbonfootprintapp.R
import com.julie.reducecarbonfootprintapp.models.User

class UserRankingAdapter(private val context: Activity, private val users: MutableList<User>)
    : ArrayAdapter<User>(context, R.layout.user_details_component,users) {

    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        val inflater = context.layoutInflater
        val rowView = inflater.inflate(R.layout.user_details_component, null, true)

        val name = rowView.findViewById(R.id.name) as TextView
        val rank = rowView.findViewById(R.id.rank) as TextView
//        val tons = rowView.findViewById(R.id.tvTons) as TextView
//
//
        name.text =users[position].firstName+" - "+users[position].points.toString()
        rank.text= "# "+(position+1).toString()
//        tons.text = "Reduce up to " + suggestions[position].savedTonsPerYear.toString() + " tons of CO2 per year"

        return rowView
    }
}
