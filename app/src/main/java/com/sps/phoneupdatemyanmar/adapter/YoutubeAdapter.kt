package com.sps.phoneupdatemyanmar.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.sps.phoneupdatemyanmar.R
import com.sps.phoneupdatemyanmar.model_all_brands.Specificate
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_youtube.view.*
import java.util.*
import kotlin.collections.ArrayList

class YoutubeAdapter(var codeList: ArrayList<Specificate> = ArrayList(),var context: Context?) :
    RecyclerView.Adapter<YoutubeAdapter.WebViewHolder>(),Filterable {

    var filterList : ArrayList<Specificate> = ArrayList()
    init {
        filterList = codeList
    }

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
        } }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WebViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_youtube, parent, false)
        return WebViewHolder(view)
    }

    override fun getItemCount(): Int {
        return filterList.size
    }

    override fun onBindViewHolder(holder: WebViewHolder, position: Int) {
        holder.bind(filterList[position])
    }

    fun update(codeList: ArrayList<Specificate>) {
        this.codeList = codeList
        this.filterList = codeList
        notifyDataSetChanged()
    }

    override fun getFilter(): Filter {
        return object :Filter(){
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()

                if (charSearch.isEmpty()) {
                    filterList = codeList
                } else {
                    val reslutList = ArrayList<Specificate>()
                    for (row in codeList) {
                        if (row.category.name.toLowerCase(Locale.ROOT)
                                .contains(charSearch.toLowerCase(Locale.ROOT))
                        ) {
                            reslutList.add(row)
                        }
                    }
                    filterList = reslutList
                }
                val filterResult = FilterResults()
                filterResult.values = filterList
                return filterResult
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filterList = results?.values as ArrayList<Specificate>
                if (filterList.isEmpty()) {
                    Toast.makeText(context, "Not Found", Toast.LENGTH_LONG).show()
                }
                notifyDataSetChanged()
            }

        }
    }
}