package com.sps.phoneupdatemyanmar.ui.detail

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sps.phoneupdatemyanmar.R
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_detail.*

class DetailFragment : Fragment() {
    lateinit var pieData: PieData
    lateinit var pieDataSet: PieDataSet
    lateinit var pieEntries: ArrayList<PieEntry>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fromOneBrandFragment()
        hideNavigationView()
    }

    @SuppressLint("SetTextI18n")
    fun fromOneBrandFragment() {

//        detailBrand.cname,        1
//        detailBrand.battery,      2
//        detailBrand.capacity,     3
//        detailBrand.cpu,           4
//        detailBrand.display,      5
//        detailBrand.features,     6
//        detailBrand.date,         7
//        detailBrand.price,            8
//        detailBrand.selfie_camera,            9
//        detailBrand.main_camera,      10
//        detailBrand.os,           11
//        detailBrand.bname     12
        //  detailBrand.image

        val data = arguments?.let { DetailFragmentArgs.fromBundle(it) }
        detail_name.text = data?.details?.get(0) ?: "unknown"

        val battery = data?.details?.get(1) ?: 0f
        if (battery.toString().length > 4){
            getPieChartEntries(0f)
        }else{
            getPieChartEntries(battery.toString().toFloat())
        }


        detail_capacity.text = "${data?.details?.get(2) ?: "Unknown"} GB ROM"
        detail_cpu.text = data?.details?.get(3) ?: "Unknown"
        detail_display.text = data?.details?.get(4) ?: "Unknown"
        detail_feature.text = data?.details?.get(5) ?: "Unknown"
        release_date.text = "Date : ${data?.details?.get(6) ?: "Unknown"}"
        detail_price.text = "$ ${data?.details?.get(7) ?: "Unknown"}"
        detail_sefile_camera.text = "Font\n ${data?.details?.get(8) ?: "Unknown"}"
        detail_main_camera.text = "Main\n ${data?.details?.get(9) ?: "Unknown"}"
        detail_os.text = "${data?.details?.get(10) ?: "Unknown"} OS"

        if (data?.details?.get(11) != "Apple") {
            os_image.setImageResource(R.drawable.android)
        } else {
            os_image.setImageResource(R.drawable.ios)
        }

        Picasso.get().load(data?.details?.get(12)).placeholder(R.drawable.loading)
            .into(detail_image)

        detail_ram.text = "${data?.details?.get(13) ?: "Unknown"} GB RAM"


    }

    private fun getPieChartEntries(battery: Float) {
        pieEntries = ArrayList()
        pieEntries.add(PieEntry(battery, "Battery mAh"))
        pieDataSet = PieDataSet(pieEntries, "")
        pieData = PieData(pieDataSet)
        pie_chart.data = pieData

        pie_chart.animateY(3000)
        pie_chart.setDrawEntryLabels(false) // remove entry data label
        pieDataSet.valueTextColor = Color.RED
        pieDataSet.valueTextSize = 20f

        pieDataSet.color = Color.GREEN
        pie_chart.maxAngle = 180f
        pie_chart.rotationAngle = 180f
        pie_chart.isRotationEnabled = false
        pie_chart.description.isEnabled = false
        pie_chart.holeRadius = 50f
        pie_chart.setExtraOffsets(0f, 0f, 0f, -250f)

        val legend = pie_chart.legend
        legend.verticalAlignment = Legend.LegendVerticalAlignment.TOP
        legend.horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER
        legend.orientation = Legend.LegendOrientation.HORIZONTAL
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
