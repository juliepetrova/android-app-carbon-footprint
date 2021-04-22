package com.julie.reducecarbonfootprintapp.adapter

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.julie.reducecarbonfootprintapp.R
import com.julie.reducecarbonfootprintapp.models.Suggestion

class SuggestionListAdapter(private val context: Activity, private val suggestions: MutableList<Suggestion>)
    : ArrayAdapter<Suggestion>(context, R.layout.suggestion_component, suggestions) {

    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        val inflater = context.layoutInflater
        val rowView = inflater.inflate(R.layout.suggestion_component, null, true)

        val titleText = rowView.findViewById(R.id.tvTitle) as TextView
        val points = rowView.findViewById(R.id.tvPoints) as TextView
        val tons = rowView.findViewById(R.id.tvTons) as TextView


        titleText.text = suggestions[position].title
        points.text= "+ " + suggestions[position].points.toString() + "p"
        tons.text = "Reduce up to " + suggestions[position].savedTonsPerYear.toString() + " tons of CO2 per year"

        return rowView
    }
}