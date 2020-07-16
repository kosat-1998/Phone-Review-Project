package com.sps.phoneupdatemyanmar.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.sps.phoneupdatemyanmar.R
import com.sps.phoneupdatemyanmar.adapter.AutoSliderAdapter
import com.sps.phoneupdatemyanmar.viewmodel.AllBrandsViewModel
import com.smarteist.autoimageslider.SliderAnimations
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.fragment_all_brands.view.*
import kotlinx.android.synthetic.main.fragment_dashboard.*

class DashboardFragment : Fragment() {

    private lateinit var autoSliderAdapter: AutoSliderAdapter
    private lateinit var allBrandsViewModel: AllBrandsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_dashboard, container, false)
        if (root.card_nav_view != null && root.card_view != null) {
            root.card_nav_view.visibility = View.VISIBLE
            root.card_view.visibility = View.VISIBLE
            Toast.makeText(context, "view back", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(context, "view gone", Toast.LENGTH_LONG).show()
        }

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeView()
        allBrandsViewModel.loadAllBrands(context)
        toBrandNav()
        toYoutube()
        toSetting()
        toFavorite()


    }

    override fun onResume() {
        super.onResume()
        navigationBottomViewShow()
    }

    private fun observeView() {
        allBrandsViewModel = ViewModelProvider(this).get(AllBrandsViewModel::class.java)

        allBrandsViewModel.allBrands().observe(viewLifecycleOwner,
            Observer {
                autoSliderAdapter = AutoSliderAdapter(it)
                imageSlider?.apply {
                    setSliderAdapter(autoSliderAdapter)
                    startAutoCycle()
                    setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION)
                    setIndicatorEnabled(true)
                    scrollTimeInSec = 3
                }
            })
    }

    private fun navigationBottomViewShow() {
        val cardNavView = requireActivity().findViewById<View>(R.id.card_nav_view)
        val navView = requireActivity().findViewById<View>(R.id.nav_view)
        if (cardNavView != null && navView != null) {
            cardNavView.visibility = View.VISIBLE
            navView.visibility = View.VISIBLE
        }
    }

    private fun toBrandNav() {
        to_brands_navigate?.setOnClickListener {
            view?.findNavController()
                ?.navigate(R.id.action_navigation_dashboard_to_logoListFragment)
        }
    }

    private fun toYoutube() {
        to_youtube_navigate.setOnClickListener {
            view?.findNavController()?.navigate(R.id.action_navigation_dashboard_to_youtubeFragment)
        }
    }

    private fun toSetting() {
        to_setting_navigate.setOnClickListener {
            view?.findNavController()
                ?.navigate(R.id.action_navigation_dashboard_to_settingFragment)
        }
    }

    private fun toFavorite() {
        to_favorite_navigate.setOnClickListener {
            view?.findNavController()
                ?.navigate(R.id.action_navigation_dashboard_to_favoriteFragment)
        }
    }

}