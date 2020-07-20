package com.sps.phoneupdatemyanmar.ui.compare

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.sps.phoneupdatemyanmar.R
import com.sps.phoneupdatemyanmar.model_all_brands.Specificate
import com.sps.phoneupdatemyanmar.viewmodel.AllBrandsViewModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_compare.*

class CompareFragment : Fragment() {

    private lateinit var allBrandsViewModel: AllBrandsViewModel
    private lateinit var allSpecificate: ArrayList<Specificate>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_compare, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        allSpecificate = ArrayList()
        observedView()
        compareSpinner_1()
        compareSpinner_2()
        hideNavigationView()

        toBatteryChartNav()

        //moving textView
        moving_Text.setSingleLine()
        moving_Text.ellipsize = TextUtils.TruncateAt.MARQUEE
        moving_Text.marqueeRepeatLimit = -1
        moving_Text.isSelected = true

    }

    private fun observedView() {
        allBrandsViewModel = ViewModelProvider(this).get(AllBrandsViewModel::class.java)

        allBrandsViewModel.allBrands().observe(viewLifecycleOwner,
            Observer {
                allSpecificate = it
                mySpinner(allSpecificate)
                compare_view.visibility = View.VISIBLE
                progress_compare.visibility = View.GONE
            }
        )


    }

    override fun onResume() {
        super.onResume()
        allBrandsViewModel.loadAllBrands(context)
    }

    private fun mySpinner(allSpecificate: ArrayList<Specificate>) {
        val brandsName: ArrayList<String> = ArrayList()

        val brandList: List<Specificate> =
            allSpecificate.sortedWith(compareBy { it.category.name }) // ascending  // val descending = allSpecificate.sortedByDescending { it.ram }
        for (name in brandList) {
            brandsName.add(name.category.name)
        }

        val arrayAdpter = activity?.applicationContext?.let {
            ArrayAdapter(
                it,
                android.R.layout.simple_dropdown_item_1line,
                brandsName
            ) as SpinnerAdapter
        }

        spinner_1?.adapter = arrayAdpter
        spinner_2.adapter = arrayAdpter
    }

    private fun compareSpinner_1() {
        spinner_1.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

            @SuppressLint("SetTextI18n")
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {


                for (details in allSpecificate) {
                    if (details.category.name == parent?.getItemAtPosition(position).toString()) {
                        Picasso.get().load(details.image).placeholder(R.drawable.loading)
                            .into(compare_image_1)
                        name_1.text = details.category.name
                        cpu_1.text = details.cpu
                        ram_1.text = "${details.memory} GB RAM"
                        main_camera_1.text = details.main_camera
                        capacity_1.text = "${details.capacity} GB ROM"
                        sefile_camera_1.text = details.selfie_camera
                        battery_1.text = "${details.battery} mAh"
                        screen_1.text = details.display
                        feature_1.text = details.features
                        price_1.text = "$ ${details.price}"
                    }
                }


            }

        }
    }

    private fun compareSpinner_2() {
        spinner_2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

            @SuppressLint("SetTextI18n")
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                Toast.makeText(
                    context,
                    parent?.getItemAtPosition(position).toString(),
                    Toast.LENGTH_LONG
                ).show()

                for (details in allSpecificate) {
                    if (details.category.name == parent?.getItemAtPosition(position).toString()) {
                        Picasso.get().load(details.image).placeholder(R.drawable.loading)
                            .into(compare_image_2)
                        name_2.text = details.category.name
                        cpu_2.text = details.cpu
                        ram_2.text = "${details.memory} GB RAM"
                        main_camera_2.text = details.main_camera
                        capacity_2.text = "${details.capacity} GB ROM"
                        sefile_camera_2.text = details.selfie_camera
                        battery_2.text = "${details.battery} mAh"
                        screen_2.text = details.display
                        feature_2.text = details.features
                        price_2.text = "$ ${details.price}"
                    }
                }
            }
        }
    }

    private fun hideNavigationView() {
        val cardNavView = activity?.findViewById<View>(R.id.card_nav_view)
        val navView = activity?.findViewById<View>(R.id.nav_view)
        if (cardNavView != null && navView != null) {
            cardNavView.visibility = View.GONE
            navView.visibility = View.GONE
        }
    }

    private fun toBatteryChartNav() {
        //  val toBrands: View? = activity?.findViewById(R.id.to_brands_navigate)

        to_battery_chart?.setOnClickListener {
            view?.findNavController()?.navigate(R.id.action_navigation_compare_to_chartFragment)
        }
    }


}