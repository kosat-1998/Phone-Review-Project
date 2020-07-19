package com.sps.phoneupdatemyanmar.ui.save

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.sps.phoneupdatemyanmar.R
import com.sps.phoneupdatemyanmar.adapter.FavoriteListAdapter
import com.sps.phoneupdatemyanmar.model_all_brands.Specificate
import com.sps.phoneupdatemyanmar.viewmodel.AllBrandsViewModel
import kotlinx.android.synthetic.main.fragment_favorite.*


class FavoriteFragment : Fragment(), FavoriteListAdapter.FavoriteClick {

    private lateinit var allBrandsViewModel: AllBrandsViewModel
    private var favoriteListAdapter: FavoriteListAdapter = FavoriteListAdapter(context = context)


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        hideNavigationView()
        observedView()

    }

    private fun hideNavigationView() {
        val cardNavView = activity?.findViewById<View>(R.id.card_nav_view)
        val navView = activity?.findViewById<View>(R.id.nav_view)
        if (cardNavView != null && navView != null) {
            cardNavView.visibility = View.GONE
            navView.visibility = View.GONE
        }
    }

    private fun observedView() {
        allBrandsViewModel = ViewModelProvider(this).get(AllBrandsViewModel::class.java)

        allBrandsViewModel.allBrands().observe(viewLifecycleOwner,
            Observer {
                checkfavorite(it)
                if (favoriteListAdapter.specificate.isEmpty()) {
                    no_data.visibility = View.VISIBLE
                    all_delete_favorite.hide()
                    loading_favorite.visibility = View.GONE


                } else {
                    no_data.visibility = View.GONE
                    all_delete_favorite.show()
                    deleteAllFavorite(it)
                }
            }
        )
    }

    override fun onResume() {
        super.onResume()
        allBrandsViewModel.loadAllBrands(context)
    }

    // to check is favorite or not
    private fun checkfavorite(specificate: ArrayList<Specificate>) {

        // for sharePreference
        val sharedPrefFile = "TEST_SHARED_PREFERENCE"
        val sharePreference: SharedPreferences = requireContext().getSharedPreferences(
            sharedPrefFile,
            Context.MODE_PRIVATE
        )
        val favoriteList: ArrayList<Specificate> = ArrayList()
        for (check in specificate) {
            val checkID = sharePreference.getString(check.category.name, "0")
            if (check.category.id == checkID?.toInt()) {
                favoriteList.add(check)
                recycler_favorite.visibility = View.VISIBLE
                loading_favorite.visibility = View.GONE
            }
        }
        favoriteListAdapter = FavoriteListAdapter(favoriteList,context)
        recycler_favorite.adapter = favoriteListAdapter
        favoriteListAdapter.setClickListener(this)

    }

    // to delete all favorite
    private fun deleteAllFavorite(specificate: ArrayList<Specificate>) {
        // for sharePreference
        val sharedPrefFile = "TEST_SHARED_PREFERENCE"
        val sharePreference: SharedPreferences = requireContext().getSharedPreferences(
            sharedPrefFile,
            Context.MODE_PRIVATE
        )
        all_delete_favorite.setOnClickListener {
            if (favoriteListAdapter.specificate.isEmpty()) {
                Toast.makeText(context, "No Favorite List", Toast.LENGTH_SHORT).show()
            } else {
                val builder = AlertDialog.Builder(requireContext())
                builder.setTitle(R.string.warn)
                builder.setMessage(R.string.dialogMessage)
                builder.setIcon(R.drawable.ic_warn)

                //performing positive action
                builder.setPositiveButton("Yes") { dialog, which ->
                    val editor = sharePreference.edit()
                    editor.clear()
                    editor.apply()
                    editor.commit()
                  //  favoriteListAdapter = FavoriteListAdapter(context = context)
                    val favoriteList: ArrayList<Specificate> = ArrayList()
                    for (check in specificate) {
                        val checkID = sharePreference.getString(check.category.name, "0")
                        if (check.category.id == checkID?.toInt()) {
                            favoriteList.add(check)
                            recycler_favorite.visibility = View.VISIBLE
                            loading_favorite.visibility = View.GONE
                        }
                    }
                    favoriteListAdapter = FavoriteListAdapter(favoriteList,context)
                    favoriteListAdapter.notifyDataSetChanged()
                    recycler_favorite.adapter = favoriteListAdapter
                    no_data.visibility = View.VISIBLE
                }

                //performing cancel action
                builder.setNegativeButton("No") { dialog, which ->
                    Toast.makeText(context, "Cancel", Toast.LENGTH_LONG).show()
                }
                // Create the AlertDialog
                val alertDialog: AlertDialog = builder.create()
                // Set other dialog properties
                alertDialog.setCancelable(false)
                alertDialog.show()
            }
        }
    }

    override fun onClick(specificate: Specificate) {
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
        val action = FavoriteFragmentDirections.actionFavoriteFragmentToDetailFragment(details)
        findNavController().navigate(action)
    }
}