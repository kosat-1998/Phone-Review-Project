package com.sps.phoneupdatemyanmar.ui.chart

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.github.mikephil.charting.charts.BarChart
import com.sps.phoneupdatemyanmar.R
import com.sps.phoneupdatemyanmar.model_all_brands.Specificate
import com.sps.phoneupdatemyanmar.viewmodel.AllBrandsViewModel
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import kotlinx.android.synthetic.main.fragment_chart.*


class ChartFragment : Fragment() {

    private lateinit var allBrandsViewModel: AllBrandsViewModel

    lateinit var barData: BarData
    lateinit var barDataSet: BarDataSet
    lateinit var barEntries: ArrayList<BarEntry>


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

        //switch view
        var view_switch = false
        change_view.setOnClickListener {
            if (!view_switch) {
                specifications_view.visibility = View.GONE
                bnenchmark_view.visibility = View.VISIBLE
                change_view.setImageResource(R.drawable.ic_second_page)
                view_switch = true
            } else {
                view_switch = false
                specifications_view.visibility = View.VISIBLE
                bnenchmark_view.visibility = View.GONE
                change_view.setImageResource(R.drawable.ic_first_page)
            }
        }
        hideNavigationView()
    }

    private fun observedView() {
        allBrandsViewModel = ViewModelProvider(this).get(AllBrandsViewModel::class.java)

        allBrandsViewModel.allBrands().observe(viewLifecycleOwner,
            Observer {
                getBarChartEntriesRam(it)
                getBarChartEntriesCapacity(it)
                getBarChartEntriesBattery(it)
                getBarChartEntriesPrice(it)
                getBnenchMarkData(it)
            }
        )
    }

    override fun onResume() {
        super.onResume()
        allBrandsViewModel.loadAllBrands(context)
    }

    private fun getBarChartEntriesRam(ramList: List<Specificate>) {
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
        ram_chart.setFitBars(true)
        ram_chart.setVisibleXRangeMaximum(20f)
        ram_chart.setScaleEnabled(false)
        ram_chart.axisRight.isEnabled = false //right axis label false

        // description
        ram_chart.description.text = "RAM COMPARISON"
        ram_chart.description.textSize = 5f

    }

    private fun getBarChartEntriesCapacity(capacityList: List<Specificate>) {
        val xAxis = capacity_chart.xAxis
        val label: ArrayList<String> = ArrayList()
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

        xAxis.textColor = Color.BLUE
        xAxis.setCenterAxisLabels(false)
        xAxis.granularity = 1f
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.labelRotationAngle = -90f
        capacity_chart.invalidate()
        xAxis.valueFormatter = IndexAxisValueFormatter(label) // xAxis label value format
        xAxis.labelCount = capacityList.size

        barDataSet = BarDataSet(barEntries, "Capacity")
        barData = BarData(barDataSet)
        capacity_chart.data = barData
        capacity_chart.animateY(1200)
        capacity_chart.setFitBars(true)
        capacity_chart.setVisibleXRangeMaximum(20f)
        capacity_chart.setScaleEnabled(false)
        capacity_chart.axisRight.isEnabled = false //right axis label false

        // description
        capacity_chart.description.text = "Capacity COMPARISON"
        capacity_chart.description.textSize = 5f

    }

    private fun getBarChartEntriesBattery(batteryList: List<Specificate>) {
        val xAxis = battery_chart.xAxis
        val label: ArrayList<String> = ArrayList()
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

        xAxis.textColor = Color.BLUE
        xAxis.setCenterAxisLabels(false)
        xAxis.granularity = 1f
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.labelRotationAngle = -90f
        battery_chart.invalidate()
        xAxis.valueFormatter = IndexAxisValueFormatter(label) // xAxis label value format
        xAxis.labelCount = batteryList.size

        barDataSet = BarDataSet(barEntries, "Battery")
        barData = BarData(barDataSet)
        battery_chart.data = barData
        battery_chart.animateY(1200)
        battery_chart.setFitBars(true)
        battery_chart.setVisibleXRangeMaximum(20f)
        battery_chart.setScaleEnabled(false)
        battery_chart.axisRight.isEnabled = false //right axis label false

        // description
        battery_chart.description.text = "Battery COMPARISON"
        battery_chart.description.textSize = 5f
    }

    private fun getBarChartEntriesPrice(priceList: List<Specificate>) {
        val xAxis = price_chart.xAxis
        val label: ArrayList<String> = ArrayList()
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

        xAxis.textColor = Color.BLUE
        xAxis.setCenterAxisLabels(false)
        xAxis.granularity = 1f
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.labelRotationAngle = -90f
        price_chart.invalidate()
        xAxis.valueFormatter = IndexAxisValueFormatter(label) // xAxis label value format
        xAxis.labelCount = priceList.size

        barDataSet = BarDataSet(barEntries, "Price")
        barData = BarData(barDataSet)
        price_chart.data = barData
        price_chart.animateY(1200)
        price_chart.setFitBars(true)
        price_chart.setVisibleXRangeMaximum(15f)
        price_chart.setScaleEnabled(false)
        price_chart.axisRight.isEnabled = false //right axis label false
        // description
        price_chart.description.text = "Price COMPARISON"
        price_chart.description.textSize = 5f
    }

    // bnenchmark view
    private fun getBnenchMarkData(bnechmarklist: List<Specificate>) {

        val xAxis = bnenchmark_graph.xAxis
        val label: ArrayList<String> = ArrayList()
        val barTotalRank = ArrayList<BarEntry>()
        val barCpuRank = ArrayList<BarEntry>()
        val barMemoryRank = ArrayList<BarEntry>()
        val barGpuRank = ArrayList<BarEntry>()
        val barUIRank = ArrayList<BarEntry>()

        var count = 0f

        for (totalmark in bnechmarklist) {
            Log.i("count", "$count")
            if (!totalmark.total.toString().isEmpty()) {

                barTotalRank.add(BarEntry(count, totalmark.total.toString().toFloat()))
                barCpuRank.add(BarEntry(count, totalmark.cpu_rank.toString().toFloat()))
                barGpuRank.add(BarEntry(count, totalmark.gpu_rank.toString().toFloat()))
                barMemoryRank.add(BarEntry(count, totalmark.memory_rank.toString().toFloat()))
                barUIRank.add(BarEntry(count, totalmark.ux_rank.toString().toFloat()))

                label.add(totalmark.category.name)
                count++
            } else {
                barTotalRank.add(BarEntry(count, 99999f))
                barCpuRank.add(BarEntry(count, 88888f))
                barGpuRank.add(BarEntry(count, 33333f))
                barMemoryRank.add(BarEntry(count, 22222f))
                barUIRank.add(BarEntry(count, 66666f))

                label.add(totalmark.category.name)
                count++
            }
        }
        // xAxis alignment
        xAxis.textColor = Color.BLUE
        xAxis.position = XAxis.XAxisPosition.TOP
        xAxis.setCenterAxisLabels(true)
        xAxis.isGranularityEnabled = true
        xAxis.granularity = 1f
        xAxis.setDrawGridLinesBehindData(false)
        xAxis.axisMinimum = 1F
        xAxis.axisMaximum = bnechmarklist.size.toFloat()

        // set data
        val total_barDataSet = BarDataSet(barTotalRank, "Total Rank")
        total_barDataSet.setColors(Color.BLUE)
        val cpu_barDataSet = BarDataSet(barCpuRank, "CPU Rank")
        cpu_barDataSet.setColors(Color.RED)
        val gpu_barDataSet = BarDataSet(barGpuRank, "GPU Rank")
        gpu_barDataSet.setColors(Color.GREEN)
        val memory_barDataSet = BarDataSet(barMemoryRank, "Memory Rank")
        memory_barDataSet.setColors(Color.CYAN)
        val uI_barDataSet = BarDataSet(barUIRank, "UI Rank")
        uI_barDataSet.setColors(Color.WHITE)

        barData = BarData(
            total_barDataSet,
            cpu_barDataSet,
            gpu_barDataSet,
            memory_barDataSet,
            uI_barDataSet
        )
        bnenchmark_graph?.data = barData

        // xAxix reformat
        xAxis.valueFormatter = IndexAxisValueFormatter(label) // xAxis label value format
        xAxis.labelCount = bnechmarklist.size
        bnenchmark_graph?.setFitBars(true)
        bnenchmark_graph?.setVisibleXRangeMaximum(10F)
        bnenchmark_graph?.setScaleEnabled(false)
        bnenchmark_graph?.invalidate()
        bnenchmark_graph?.axisRight?.isEnabled = false //right axis label false

        // description
        bnenchmark_graph?.description?.text = "Rank COMPARISON"
        bnenchmark_graph?.description?.textSize = 5f

        //IMPORTANT *****
        // 5 Bar One label formula
        // (barSpace + barWidth) * 5 + groupSpace = 1

        val barSpace = 0f
        val groupSpace = 0.2f
        barData.barWidth = 0.16f
        bnenchmark_graph?.groupBars(0f, groupSpace, barSpace) // perform the "explicit" grouping
        bnenchmark_graph?.invalidate()
    }
    private fun hideNavigationView() {
        val cardNavView = activity?.findViewById<View>(R.id.card_nav_view)
        val navView = activity?.findViewById<View>(R.id.nav_view)
        if (cardNavView != null && navView != null) {
            cardNavView.visibility = View.GONE
            navView.visibility = View.GONE
        }
    }
}