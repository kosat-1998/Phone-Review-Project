package com.sps.phoneupdatemyanmar.ui.logo_list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.sps.phoneupdatemyanmar.R
import com.sps.phoneupdatemyanmar.adapter.LogosListAdapter
import com.sps.phoneupdatemyanmar.model_logos.Brand
import com.sps.phoneupdatemyanmar.viewmodel.LogoViewModel
import kotlinx.android.synthetic.main.fragment_logo_list.*

class LogoListFragment : Fragment() , LogosListAdapter.ClickListener{

    private lateinit var logoViewModel: LogoViewModel
    private lateinit var logosListAdapter: LogosListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_logo_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        logosListAdapter = LogosListAdapter()
        recycler_logos_list.apply {
            adapter = logosListAdapter
        }
        logoViewModel = ViewModelProvider(this).get(LogoViewModel::class.java)

        observedView()

        logosListAdapter.setClickListenr(this)

        loadBar()
        hideNavigationView()
    }

    private fun observedView() {

        logoViewModel.brandList().observe(viewLifecycleOwner,
            Observer {
                logosListAdapter.updateBrandsList(it)
            })
    }

    override fun onResume() {
        super.onResume()
        logoViewModel.loadBrands(context)

    }

    override fun onClick(brand: Brand) {
        var id = brand.id

        val action = LogoListFragmentDirections.actionLogoListFragmentToBrandIDFragment(id)
        findNavController().navigate(action)
    }


    // loadBar
    private fun loadBar() {

        Toast.makeText(context, logoViewModel.loading().value.toString(), Toast.LENGTH_LONG)
            .show()
        if (logoViewModel.loading().value == true) {
            progress_bar_brandlogo_load.visibility = View.GONE
            recycler_logos_list.visibility = View.VISIBLE
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



}