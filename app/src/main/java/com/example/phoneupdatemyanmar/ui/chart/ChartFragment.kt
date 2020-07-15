package com.example.phoneupdatemyanmar.ui.chart

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.example.phoneupdatemyanmar.R
import com.example.phoneupdatemyanmar.adapter.AllBrandsAdapter
import com.example.phoneupdatemyanmar.model_all_brands.Specificate
import com.example.phoneupdatemyanmar.viewmodel.AllBrandsViewModel
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.fragment_chart.*


class ChartFragment : Fragment() {

    private lateinit var allBrandsViewModel: AllBrandsViewModel

    lateinit var barData: BarData
    lateinit var barDataSet: BarDataSet
    lateinit var barEntries: ArrayList<BarEntry>
    lateinit var battery: ArrayList<Specificate>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chart, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observedView()
    }

    private fun observedView() {
        allBrandsViewModel = ViewModelProvider(this).get(AllBrandsViewModel::class.java)

        allBrandsViewModel.allBrands().observe(viewLifecycleOwner,
            Observer {
                getBarChartEntriesRam(it)
                getBarChartEntriesCapacity(it)
                getBarChartEntriesBattery(it)
                getBarChartEntriesPrice(it)
            }
        )
    }

    override fun onResume() {
        super.onResume()
        allBrandsViewModel.loadAllBrands(context)
    }

    private fun getBarChartEntriesRam(ramList: List<Specificate>) {

        val ascending = ramList.sortedWith(compareBy { it.memory })
        val descending = ramList.sortedByDescending { it.memory }

        ascending_btn_memory.setOnClickListener {
            getBarChartEntriesRam(ascending)
        }
        descending_btn_memory.setOnClickListener {
            getBarChartEntriesRam(descending)
        }
        val xAxis = ram_chart.xAxis
        val label: ArrayList<String> = ArrayList()
        barEntries = ArrayList()
        var count = 0f

        for (ram in ramList) {
            Log.i("count", "$count")

            if (ram.memory.trim().length < 3) {
                barEntries.add(BarEntry(count, ram.memory.toFloat()))
                label.add(ram.category.name)
                count++
            }
        }
        xAxis.textColor = Color.BLUE
        xAxis.setCenterAxisLabels(false)
        xAxis.granularity = 1f
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.labelRotationAngle = -90f
        ram_chart.invalidate()
        xAxis.valueFormatter = IndexAxisValueFormatter(label) // xAxis label value format
        xAxis.labelCount = ramList.size

        barDataSet = BarDataSet(barEntries, "RAM")
        barData = BarData(barDataSet)
        ram_chart.data = barData
        ram_chart.animateY(1200)

        //  barDataSet.setColors(Color.BLUE, Color.GRAY, Color.RED, Color.CYAN)
        ram_chart.setFitBars(true)
        ram_chart.setVisibleXRangeMaximum(20f)
        ram_chart.setScaleEnabled(false)
        ram_chart.axisRight.isEnabled = false //right axis label false

        // description
        ram_chart.description.text = "RAM COMPARISON"
        ram_chart.description.textSize = 5f

    }

    private fun getBarChartEntriesCapacity(capacityList: List<Specificate>) {

        val ascending = capacityList.sortedWith(compareBy { it.capacity })
        val descending = capacityList.sortedByDescending { it.capacity }

        ascending_btn_capacity.setOnClickListener {
            getBarChartEntriesCapacity(ascending)
        }
        descending_btn_capacity.setOnClickListener {
            getBarChartEntriesCapacity(descending)
        }
        val xAxis = capacity_chart.xAxis
        val label: ArrayList<String> = ArrayList()

        xAxis.textColor = Color.BLUE
        xAxis.setCenterAxisLabels(false)
        xAxis.granularity = 1f
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.labelRotationAngle = -90f
        capacity_chart.invalidate()
        xAxis.valueFormatter = IndexAxisValueFormatter(label) // xAxis label value format
        xAxis.labelCount = capacityList.size

        barEntries = ArrayList()
        var count = 0f

        for (capacity in capacityList) {
            Log.i("count", "$count")
            if (capacity.capacity.trim().length < 5) {
                barEntries.add(BarEntry(count, capacity.capacity.toFloat()))
                label.add(capacity.category.name)
                count++
            }

        }

        barDataSet = BarDataSet(barEntries, "Capacity")
        barData = BarData(barDataSet)
        capacity_chart.data = barData
        capacity_chart.animateY(1200)

        //  barDataSet.setColors(Color.BLUE, Color.GRAY, Color.RED, Color.CYAN)
        capacity_chart.setFitBars(true)
        capacity_chart.setVisibleXRangeMaximum(20f)
        capacity_chart.setScaleEnabled(false)
        capacity_chart.axisRight.isEnabled = false //right axis label false

        // description
        capacity_chart.description.text = "Capacity COMPARISON"
        capacity_chart.description.textSize = 5f

    }

    private fun getBarChartEntriesBattery(batteryList: List<Specificate>) {

        val ascending = batteryList.sortedWith(compareBy { it.battery })
        val descending = batteryList.sortedByDescending { it.battery }

        ascending_btn_battery.setOnClickListener {
            getBarChartEntriesBattery(ascending)
        }
        descending_btn_battery.setOnClickListener {
            getBarChartEntriesBattery(descending)
        }
        val xAxis = battery_chart.xAxis
        val label: ArrayList<String> = ArrayList()

        xAxis.textColor = Color.BLUE
        xAxis.setCenterAxisLabels(false)
        xAxis.granularity = 1f
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.labelRotationAngle = -90f
        battery_chart.invalidate()
        xAxis.valueFormatter = IndexAxisValueFormatter(label) // xAxis label value format
        xAxis.labelCount = batteryList.size

        barEntries = ArrayList()
        var count = 0f

        for (battery in batteryList) {
            Log.i("count", "$count")

            if (battery.battery.trim().length < 5) {
                barEntries.add(BarEntry(count, battery.battery.toFloat()))
                label.add(battery.category.name)
                count++
            }
        }

        barDataSet = BarDataSet(barEntries, "Battery")
        barData = BarData(barDataSet)
        battery_chart.data = barData
        battery_chart.animateY(1200)

        //  barDataSet.setColors(Color.BLUE, Color.GRAY, Color.RED, Color.CYAN)
        battery_chart.setFitBars(true)
        battery_chart.setVisibleXRangeMaximum(20f)
        battery_chart.setScaleEnabled(false)
        battery_chart.axisRight.isEnabled = false //right axis label false

        // description
        battery_chart.description.text = "Battery COMPARISON"
        battery_chart.description.textSize = 5f

    }

    private fun getBarChartEntriesPrice(priceList: List<Specificate>) {

        val ascending = priceList.sortedWith(compareBy { it.price.trim() })
        val descending = priceList.sortedByDescending { it.price.trim() }

        ascending_btn_price.setOnClickListener {
            getBarChartEntriesPrice(ascending)
        }
        descending_btn_price.setOnClickListener {
            getBarChartEntriesPrice(descending)
        }
        val xAxis = price_chart.xAxis
        val label: ArrayList<String> = ArrayList()
        for (name in priceList) {

        }
        xAxis.textColor = Color.BLUE
        xAxis.setCenterAxisLabels(false)
        xAxis.granularity = 1f
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.labelRotationAngle = -90f
        price_chart.invalidate()
        xAxis.valueFormatter = IndexAxisValueFormatter(label) // xAxis label value format
        xAxis.labelCount = priceList.size


        barEntries = ArrayList()
        var count = 0f

        for (price in priceList) {
            Log.i("count", "$count")

            if (price.price.trim().length < 5) {
                barEntries.add(BarEntry(count, price.price.toFloat()))
                label.add(price.category.name)
                count++
            }

        }


        barDataSet = BarDataSet(barEntries, "Price")
        barData = BarData(barDataSet)
        price_chart.data = barData
        price_chart.animateY(1200)

        //  barDataSet.setColors(Color.BLUE, Color.GRAY, Color.RED, Color.CYAN)
        price_chart.setFitBars(true)
        price_chart.setVisibleXRangeMaximum(15f)
        price_chart.setScaleEnabled(false)
        price_chart.axisRight.isEnabled = false //right axis label false

        // description
        price_chart.description.text = "Price COMPARISON"
        price_chart.description.textSize = 5f

    }
}