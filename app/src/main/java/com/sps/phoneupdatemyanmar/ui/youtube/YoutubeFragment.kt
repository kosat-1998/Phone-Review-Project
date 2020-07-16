package com.sps.phoneupdatemyanmar.ui.youtube

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.sps.phoneupdatemyanmar.R
import com.sps.phoneupdatemyanmar.adapter.YoutubeAdapter
import com.sps.phoneupdatemyanmar.viewmodel.AllBrandsViewModel
import kotlinx.android.synthetic.main.fragment_youtube.*
import kotlinx.android.synthetic.main.item_youtube.*

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

        youtubeAdapter = YoutubeAdapter()
        youtube_recycler.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = youtubeAdapter
        }
        observedView()
        hideNavigationView()

    }
    private fun observedView() {
        allBrandsViewModel = ViewModelProvider(this).get(AllBrandsViewModel::class.java)

        allBrandsViewModel.allBrands().observe(viewLifecycleOwner,
            Observer {
                youtubeAdapter.update(it)
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

    fun oncreate(){
        youtube_web.webViewClient = object : WebViewClient(){

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                progress_bar_youtube.visibility = View.GONE
            }

        }
    }

}