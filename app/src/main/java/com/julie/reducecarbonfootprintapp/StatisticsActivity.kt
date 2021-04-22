package com.julie.reducecarbonfootprintapp

import android.os.Bundle
import androidx.appcompat.app.ActionBarDrawerToggle
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter


class StatisticsActivity : BaseActivity() {

    // variable for our bar chart
    private lateinit var barChart: BarChart

    // variable for our bar data set.
    private lateinit var barDataSet1: BarDataSet
    private lateinit var barDataSet2: BarDataSet

    // array list for storing entries.
    private lateinit var barEntries: ArrayList<BarEntry>

    // creating a string array for displaying days.
    var months = arrayOf("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec", "Jan", "Feb", "Mar")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_statistics)

        //        Navigation
        mDrawerLayout = findViewById(R.id.drawer)
        mToggle = ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close)

        mDrawerLayout.addDrawerListener(mToggle)
        mToggle.syncState()
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)
        setNavigationViewListener()
        //       Navigation end

        // initializing variable for bar chart.
        barChart = findViewById(R.id.idBarChart);

        // creating a new bar data set.
        barDataSet1 = BarDataSet(getBarEntriesOne(), "First Set");
        barDataSet1.setColor(getApplicationContext().getResources().getColor(R.color.colorAccent));
//        barDataSet2 = BarDataSet(getBarEntriesTwo(), "Second Set");
//        barDataSet2.setColor(R.color.redTextColor);

        // below line is to add bar data set to our bar data.
        var data = BarData(barDataSet1) as BarData

        // after adding data to our bar data we
        // are setting that data to our bar chart.
        barChart.setData(data);

        // below line is to remove description
        // label of our bar chart.
        barChart.getDescription().setEnabled(true);

        // below line is to get x axis
        // of our bar chart.
        var xAxis = barChart.getXAxis() as XAxis

        // below line is to set value formatter to our x-axis and
        // we are adding our days to our x axis.
        xAxis.setValueFormatter(IndexAxisValueFormatter(months));

        // below line is to set center axis
        // labels to our bar chart.
//        xAxis.setCenterAxisLabels(true);

        // below line is to set position
        // to our x-axis to bottom.
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        xAxis.gridLineWidth = 0.1f

        // below line is to set granularity
        // to our x axis labels.
//        xAxis.setGranularity(2f);

        // below line is to enable
        // granularity to our x axis.
//        xAxis.setGranularityEnabled(true);

        // below line is to make our
        // bar chart as draggable.
        barChart.setDragEnabled(true);

        // below line is to make visible
        // range for our bar chart.
        barChart.setVisibleXRangeMaximum(8f);

        // we are setting width of
        // bar in below line.
        data.setBarWidth(0.6f);
        xAxis.xOffset =0f
        // below line is to set minimum
        // axis to our chart.
        barChart.getXAxis().setAxisMinimum(0f);

        // below line is to
        // animate our chart.
        barChart.animate();

        barChart.getXAxis().setSpaceMax(4f)

//        xAxis.setBarSpacePercent(10)


        barChart.invalidate();

    }

    private fun getBarEntriesOne(): ArrayList<BarEntry> {

        // creating a new array list
        barEntries = ArrayList()

        // adding new entry to our array list with bar
        // entry and passing x and y axis value to it.
        barEntries.add(BarEntry(1f, 10f))
        barEntries.add(BarEntry(2f, 7f))
        barEntries.add(BarEntry(3f, 6f))
        barEntries.add(BarEntry(4f, 6f))
        barEntries.add(BarEntry(5f, 6f))
        barEntries.add(BarEntry(6f, 5f))
        barEntries.add(BarEntry(7f, 4f))
        barEntries.add(BarEntry(8f, 4f))
        barEntries.add(BarEntry(9f, 4f))
        barEntries.add(BarEntry(10f, 3f))
        barEntries.add(BarEntry(11f, 3f))
        barEntries.add(BarEntry(12f, 2f))
        barEntries.add(BarEntry(13f, 3f))
        barEntries.add(BarEntry(14f, 3f))
        barEntries.add(BarEntry(15f, 2f))
        return barEntries
    }

}