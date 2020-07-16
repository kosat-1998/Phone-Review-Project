package com.sps.phoneupdatemyanmar.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sps.phoneupdatemyanmar.R
import com.sps.phoneupdatemyanmar.model_all_brands.Specificate
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_youtube.view.*

class YoutubeAdapter(var codeList: ArrayList<Specificate> = ArrayList()) :
    RecyclerView.Adapter<YoutubeAdapter.WebViewHolder>() {
    inner class WebViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview) {
        @SuppressLint("SetJavaScriptEnabled")
        fun bind(code: Specificate) {

            itemView.brand_name.text = code.category.name
            Picasso.get().load(code.image).placeholder(R.drawable.loading)
                .into(itemView.brand_image)
            itemView.youtube_web.settings.javaScriptEnabled = true
            if (code.youtube != null) {
                itemView.youtube_web.loadUrl("https://www.youtube.com/embed/${code.youtube}")
            } else {

                itemView.youtube_web.loadUrl("https://www.youtube.com/embed/yTGvKozOYDM")
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WebViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_youtube, parent, false)
        return WebViewHolder(view)
    }

    override fun getItemCount(): Int {
        return codeList.size
    }

    override fun onBindViewHolder(holder: WebViewHolder, position: Int) {
        holder.bind(codeList[position])
    }

    fun update(codeList: ArrayList<Specificate>) {
        this.codeList = codeList
        notifyDataSetChanged()
    }
}