package com.example.phoneupdatemyanmar.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.phoneupdatemyanmar.R
import com.example.phoneupdatemyanmar.model_all_brands.Specificate
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_all_brands.view.*
import java.util.*
import kotlin.collections.ArrayList

class AllBrandsAdapter(var allSpec: ArrayList<Specificate> = ArrayList(), var context: Context?) :
    RecyclerView.Adapter<AllBrandsAdapter.AllBrandsViewHolder>(), Filterable {

    private var clicklistener: ClickListener? = null

    fun setClickListener(clicklistener: ClickListener) {
        this.clicklistener = clicklistener
    }

    var filterList = ArrayList<Specificate>()

    init {
        filterList = allSpec
    }

    inner class AllBrandsViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview),
        View.OnClickListener {

        private lateinit var allSpe: Specificate

        init {
            itemview.setOnClickListener(this)
        }

        fun bind(allSpe: Specificate) {
            this.allSpe = allSpe
            itemView.all_name.text = allSpe.category.name
            Picasso.get().load(allSpe.image).placeholder(R.drawable.loading)
                .into(itemView.all_image)
        }

        override fun onClick(v: View?) {
            clicklistener?.onCLick(allSpe)
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllBrandsViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_all_brands, parent, false)
        return AllBrandsViewHolder(view)
    }

    override fun getItemCount(): Int {
        return filterList.size
    }

    override fun onBindViewHolder(holder: AllBrandsViewHolder, position: Int) {
        holder.bind(filterList[position])
    }

    fun update(allSpe: ArrayList<Specificate>) {
        this.allSpec = allSpe
        this.filterList = allSpe
        notifyDataSetChanged()
    }

    // search view filter

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                var charSearch = constraint.toString()

                if (charSearch.isEmpty()) {
                    filterList = allSpec
                } else {
                    var reslutList = ArrayList<Specificate>()
                    for (row in allSpec) {
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

    interface ClickListener {
        fun onCLick(all: Specificate)
    }

}