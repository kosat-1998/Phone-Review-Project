package com.sps.phoneupdatemyanmar.ui.allbrands

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sps.phoneupdatemyanmar.R
import com.sps.phoneupdatemyanmar.adapter.AllBrandsAdapter
import com.sps.phoneupdatemyanmar.model_all_brands.Specificate
import com.sps.phoneupdatemyanmar.viewmodel.AllBrandsViewModel
import kotlinx.android.synthetic.main.fragment_all_brands.*

class AllBrandsFragment : Fragment(),AllBrandsAdapter.ClickListener {

    private lateinit var allBrandsViewModel: AllBrandsViewModel
    private lateinit var allBrandsAdapter: AllBrandsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_all_brands, container, false)
        return root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        floating_button.hide()
        allBrandsAdapter = AllBrandsAdapter(context = parentFragment?.context)
        recycler_all_brands.apply {
            adapter = allBrandsAdapter
//            layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
        }
        observedView()
        searchView()
        floatingBottomShowOrHide()
        loadBar()
        allBrandsAdapter.setClickListener(this)
    }

    // view show or hide
    private fun floatingBottomShowOrHide() {

        recycler_all_brands.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val cardNavView = activity?.findViewById<View>(R.id.card_nav_view)
                val navView = activity?.findViewById<View>(R.id.nav_view)
                if (dy > 0) {
                    if (floating_button != null) {

                        floating_button.hide()
                        card_view.visibility = View.GONE

                    }
                    if (cardNavView != null && navView != null) {
                        cardNavView.visibility = View.GONE
                        navView.visibility = View.GONE

                    }

                } else if (dy < 0) {
                    if (floating_button != null) {
                        floating_button.show()
                    }
                    card_view.visibility = View.VISIBLE
                    if (cardNavView != null && navView != null) {
                        cardNavView.visibility = View.VISIBLE
                        navView.visibility = View.VISIBLE
                    }
                }
            }
        })
        upTop()
    }

    // to get recyclerview start position
    private fun upTop() {
        floating_button.setOnClickListener {
            recycler_all_brands.scrollToPosition(0)
            card_view.visibility = View.VISIBLE
        }
    }

    // recyclerview search view filter
    private fun searchView() {
        action_search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                floating_button.hide()
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                floating_button.hide()
                allBrandsAdapter.filter.filter(newText)
                return false
            }

        }
        )
    }

    private fun observedView() {
        allBrandsViewModel = ViewModelProvider(this).get(AllBrandsViewModel::class.java)

        allBrandsViewModel.allBrands().observe(viewLifecycleOwner,
            Observer {
                allBrandsAdapter.update(it)
            }
        )
    }

    override fun onResume() {
        super.onResume()
        allBrandsAdapter.notifyDataSetChanged()
        allBrandsViewModel.loadAllBrands(context)
    }

    private fun loadBar() {

        allBrandsAdapter.notifyDataSetChanged()
        Toast.makeText(
            context,
            allBrandsAdapter.allSpec.size.toString(),
            Toast.LENGTH_LONG
        ).show()
        if (allBrandsAdapter.allSpec.size > 0) {
            progress_bar_allbrands_load.visibility = View.GONE
            progress_bar_allbrands_load.visibility = View.VISIBLE
        }
    }

    override fun onCLick(all: Specificate) {

        val details : Array<String> = arrayOf(
            all.category.name,
            all.battery,
            all.capacity,
            all.cpu,
            all.display,
            all.features,
            all.date,
            all.price,
            all.selfie_camera,
            all.main_camera,
            all.os,
            all.category.brand.bname,
            all.image,
            all.memory
        )
        val action = AllBrandsFragmentDirections.actionNavigationAllBrandsToDetailFragment(details)
        findNavController().navigate(action)
    }



}
