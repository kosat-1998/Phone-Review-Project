package com.sps.phoneupdatemyanmar.ui.youtube

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.sps.phoneupdatemyanmar.R
import com.sps.phoneupdatemyanmar.adapter.YoutubeAdapter
import com.sps.phoneupdatemyanmar.viewmodel.AllBrandsViewModel
import kotlinx.android.synthetic.main.fragment_youtube.*

class YoutubeFragment : Fragment() {

    private lateinit var allBrandsViewModel: AllBrandsViewModel
    private lateinit var youtubeAdapter: YoutubeAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_youtube, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        youtubeAdapter = YoutubeAdapter(context = context)
        youtube_recycler.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = youtubeAdapter
        }
        observedView()
        hideNavigationView()
        serachView()
    }

    private fun observedView() {
        allBrandsViewModel = ViewModelProvider(this).get(AllBrandsViewModel::class.java)

        allBrandsViewModel.allBrands().observe(viewLifecycleOwner,
            Observer {
                youtubeAdapter.update(it)
                progressBar()
            }
        )
    }

    override fun onResume() {
        super.onResume()
        youtubeAdapter.notifyDataSetChanged()
        allBrandsViewModel.loadAllBrands(context)
    }

    private fun hideNavigationView() {
        val cardNavView = activity?.findViewById<View>(R.id.card_nav_view)
        val navView = activity?.findViewById<View>(R.id.nav_view)
        if (cardNavView != null && navView != null) {
            cardNavView.visibility = View.GONE
            navView.visibility = View.GONE
        }
    }

    private fun progressBar() {
        youtube_progress_bar.visibility = View.GONE
        youtube_recycler.visibility = View.VISIBLE
        youtube_search_card_view.visibility = View.VISIBLE
    }

    private fun serachView() {
        youtube_search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {

                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                youtubeAdapter.filter.filter(newText)
                return false
            }

        })
    }

}