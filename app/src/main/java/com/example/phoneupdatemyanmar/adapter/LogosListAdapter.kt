package com.example.phoneupdatemyanmar.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.phoneupdatemyanmar.R
import com.example.phoneupdatemyanmar.model_logos.Brand
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_logo_list.view.*

class LogosListAdapter(var brand: ArrayList<Brand> = ArrayList()) :
    RecyclerView.Adapter<LogosListAdapter.BrandListViewHolder>() {


    private var clickListener: ClickListener? = null

    fun setClickListenr(clickListener: ClickListener) {
        this.clickListener = clickListener
    }


    inner class BrandListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {

        private lateinit var brand: Brand

        init {
            itemView.setOnClickListener(this)
        }

        fun brandList(brand: Brand) {
            this.brand = brand
            //  itemView.b_name.text = brand.bname
            Picasso.get().load(brand.image).placeholder(R.drawable.loading)
                .into(itemView.logo_image)
        }

        override fun onClick(v: View?) {
            clickListener?.onClick(brand)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BrandListViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_logo_list, parent, false)
        return BrandListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return brand.size
    }

    override fun onBindViewHolder(holder: BrandListViewHolder, position: Int) {
        holder.brandList(brand[position])
    }

    fun updateBrandsList(brand: ArrayList<Brand>) {
        this.brand = brand
        notifyDataSetChanged()

    }

    interface ClickListener {
        fun onClick(brand: Brand)
    }

}