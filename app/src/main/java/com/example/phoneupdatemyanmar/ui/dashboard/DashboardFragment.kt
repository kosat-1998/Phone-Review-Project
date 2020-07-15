package com.example.phoneupdatemyanmar.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.phoneupdatemyanmar.R
import com.example.phoneupdatemyanmar.adapter.AutoSliderAdapter
import com.example.phoneupdatemyanmar.viewmodel.AllBrandsViewModel
import com.smarteist.autoimageslider.SliderAnimations
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
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navigationBottomViewShow()

        //    imageSlide()
        autoSliderAdapter = AutoSliderAdapter()

        observeView()
        toBrandNav()
        toYoutube()


    }

    override fun onResume() {
        super.onResume()
        allBrandsViewModel.loadAllBrands(context)
        imageSlider?.apply {
            setSliderAdapter(autoSliderAdapter)
            startAutoCycle()
            setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION)
            setIndicatorEnabled(true)
//            indicatorSelectedColor = Color.WHITE
//            indicatorUnselectedColor = Color.GRAY
            scrollTimeInSec = 3
        }

    }

    private fun observeView() {
        allBrandsViewModel = ViewModelProvider(this).get(AllBrandsViewModel::class.java)

        allBrandsViewModel.allBrands().observe(viewLifecycleOwner,
            Observer {
                autoSliderAdapter.updateItem(it)
            })
    }

    private fun navigationBottomViewShow() {
        val cardNavView = activity?.findViewById<View>(R.id.card_nav_view)
        val navView = activity?.findViewById<View>(R.id.nav_view)
        if (cardNavView != null && navView != null) {
            cardNavView.visibility = View.VISIBLE
            navView.visibility = View.VISIBLE
        }
    }

    private fun toBrandNav() {
        //  val toBrands: View? = activity?.findViewById(R.id.to_brands_navigate)

        to_brands_navigate?.setOnClickListener {
            view?.findNavController()?.navigate(R.id.action_navigation_dashboard_to_logoListFragment)
        }
    }
    private fun toYoutube(){
        to_youtube_navigate.setOnClickListener {
            view?.findNavController()?.navigate(R.id.action_navigation_dashboard_to_youtubeFragment)
        }
    }

}