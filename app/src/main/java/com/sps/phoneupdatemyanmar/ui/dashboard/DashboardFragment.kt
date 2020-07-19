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
import androidx.navigation.fragment.findNavController
import com.sps.phoneupdatemyanmar.R
import com.sps.phoneupdatemyanmar.adapter.AutoSliderAdapter
import com.sps.phoneupdatemyanmar.viewmodel.AllBrandsViewModel
import com.smarteist.autoimageslider.SliderAnimations
import com.sps.phoneupdatemyanmar.model_all_brands.Specificate
import kotlinx.android.synthetic.main.fragment_dashboard.*
import java.util.*

class DashboardFragment : Fragment(), AutoSliderAdapter.ImageSetOnClickListener {

    private lateinit var autoSliderAdapter: AutoSliderAdapter
    private lateinit var allBrandsViewModel: AllBrandsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_dashboard, container, false)
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
                val rand = Random()
                it.shuffle(rand)
                autoSliderAdapter = AutoSliderAdapter(it, context)
                imageSlider.visibility = View.VISIBLE
                auto_slider_image_progress_bar.visibility = View.GONE
                imageSlider?.apply {
                    setSliderAdapter(autoSliderAdapter)
                    startAutoCycle()
                    setSliderTransformAnimation(SliderAnimations.CUBEINDEPTHTRANSFORMATION)
                    setIndicatorEnabled(true)
                    scrollTimeInSec = 3
                }
                autoSliderAdapter.setImageClickListener(this)

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

    override fun onclick(specificate: Specificate) {
        val details: Array<String> = arrayOf(
            specificate.category.name,
            specificate.battery,
            specificate.capacity,
            specificate.cpu,
            specificate.display,
            specificate.features,
            specificate.date,
            specificate.price,
            specificate.selfie_camera,
            specificate.main_camera,
            specificate.os,
            specificate.category.brand.bname,
            specificate.image,
            specificate.memory,
            specificate.review
        )
        val action = DashboardFragmentDirections.actionNavigationDashboardToDetailFragment(details)
        findNavController().navigate(action)
        Toast.makeText(context, "navigate", Toast.LENGTH_SHORT).show()
    }

}