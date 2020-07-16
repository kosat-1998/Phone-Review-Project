package com.sps.phoneupdatemyanmar.ui.setting

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sps.phoneupdatemyanmar.R

class SettingFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_setting, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        hideNavigationView()
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