package com.sps.phoneupdatemyanmar.ui.allbrands

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
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
import kotlinx.android.synthetic.main.item_all_brands.view.*
import java.util.*
import kotlin.collections.ArrayList

class AllBrandsFragment : Fragment(), AllBrandsAdapter.ClickListener,
    AllBrandsAdapter.FavoriteClikListener {

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
        }
        hideNavigationView()
        observedView()
        searchView()
        floatingBottomShowOrHide()
        //   loadBar()
        allBrandsAdapter.setClickListener(this)
        allBrandsAdapter.setFavoriteClickListener(this)
    }

    // view show or hide
    private fun floatingBottomShowOrHide() {

        recycler_all_brands.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0) {
                    if (floating_button != null) {
                        floating_button.hide()
                        // card_view.visibility = View.GONE
                    }

                } else if (dy < 0) {
                    if (floating_button != null) {
                        floating_button.show()
                    }
                    card_view.visibility = View.VISIBLE
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
        val random = Random()
        allBrandsViewModel = ViewModelProvider(this).get(AllBrandsViewModel::class.java)

        allBrandsViewModel.allBrands().observe(viewLifecycleOwner,
            Observer {
                progress_bar_all.visibility = View.GONE
                recycler_all_brands.visibility = View.VISIBLE
                it.shuffle(random)
                allBrandsAdapter.update(it)
            }
        )
    }

    override fun onResume() {
        super.onResume()
        allBrandsAdapter.notifyDataSetChanged()
        allBrandsViewModel.loadAllBrands(context)
    }

    override fun onCLick(all: Specificate) {

        val details: Array<String> = arrayOf(
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
            all.memory,
            all.review
        )
        val action = AllBrandsFragmentDirections.actionNavigationAllBrandsToDetailFragment(details)
        findNavController().navigate(action)
    }

    private fun hideNavigationView() {
        val cardNavView = activity?.findViewById<View>(R.id.card_nav_view)
        val navView = activity?.findViewById<View>(R.id.nav_view)
        if (cardNavView != null && navView != null) {
            cardNavView.visibility = View.GONE
            navView.visibility = View.GONE
        }
    }

    override fun onFavoriteClick(specificate: Specificate, view: View) {

        val sharedPrefFile = "TEST_SHARED_PREFERENCE"
        val sharePreference: SharedPreferences = requireContext().getSharedPreferences(
            sharedPrefFile,
            Context.MODE_PRIVATE
        )

        val favoriteAction: SharedPreferences.Editor = sharePreference.edit()

        val check = sharePreference.getString(specificate.category.name, "0")

        if (check == specificate.category.id.toString()) {
            favoriteAction.remove(specificate.category.name)
            favoriteAction.apply()
            favoriteAction.commit()
            Toast.makeText(context, "unfavorite", Toast.LENGTH_SHORT).show()
            view.favorite_action_button.setImageResource(R.drawable.tounfavorite)

        } else {
            favoriteAction.putString(specificate.category.name, "${specificate.category.id}")
            favoriteAction.apply()
            favoriteAction.commit()
            Toast.makeText(context, "favorite", Toast.LENGTH_SHORT).show()
            view.favorite_action_button.setImageResource(R.drawable.tofavorite)
        }
    }


}
