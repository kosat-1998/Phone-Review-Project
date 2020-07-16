package com.sps.phoneupdatemyanmar.ui.brand_id

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.sps.phoneupdatemyanmar.R
import com.sps.phoneupdatemyanmar.adapter.OneBrandIDAdapter
import com.sps.phoneupdatemyanmar.model_one_brand.Specificate
import com.sps.phoneupdatemyanmar.viewmodel.OneBrandViewModel
import kotlinx.android.synthetic.main.fragment_brand_i_d.*

class BrandIDFragment : Fragment(),OneBrandIDAdapter.Clicklistener {

    private lateinit var oneBrandViewModel: OneBrandViewModel
    private lateinit var oneBrandIDAdapter: OneBrandIDAdapter
    private var id: String = "1"


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_brand_i_d, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        oneBrandIDAdapter = OneBrandIDAdapter()
        recycler_one_brand.apply {
            adapter = oneBrandIDAdapter
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        }
        observedView()
        hideNavigationView()

        oneBrandIDAdapter.setClickListener(this)
    }


    private fun observedView() {
        oneBrandViewModel = ViewModelProvider(this).get(OneBrandViewModel::class.java)

        var messageID = arguments?.let { BrandIDFragmentArgs.fromBundle(it) }
        id = messageID?.brandID.toString() ?: "1"

        oneBrandViewModel.oneBrand().observe(viewLifecycleOwner,
            Observer {
                oneBrandIDAdapter.updateOne(it)
            })
    }

    override fun onResume() {
        super.onResume()
        oneBrandViewModel.loadID(id)
    }


    private fun hideNavigationView() {
        val cardNavView = activity?.findViewById<View>(R.id.card_nav_view)
        val navView = activity?.findViewById<View>(R.id.nav_view)
        if (cardNavView != null && navView != null) {
            cardNavView.visibility = View.GONE
            navView.visibility = View.GONE
        }
    }

    override fun onClick(detailBrand: Specificate) {
        val details : Array<String> = arrayOf(
            detailBrand.cname,
            detailBrand.battery,
            detailBrand.capacity,
            detailBrand.cpu,
            detailBrand.display,
            detailBrand.features,
            detailBrand.date,
            detailBrand.price,
            detailBrand.selfie_camera,
            detailBrand.main_camera,
            detailBrand.os,
            detailBrand.bname,
            detailBrand.image,
            detailBrand.memory
        )
        val action = BrandIDFragmentDirections.actionBrandIDFragmentToDetailFragment(details)
        findNavController().navigate(action)
    }
}