package com.example.phoneupdatemyanmar.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.phoneupdatemyanmar.R
import com.example.phoneupdatemyanmar.model_one_brand.Specificate
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_one_brand.view.*

class OneBrandIDAdapter(var oneList: ArrayList<Specificate> = ArrayList()) :
    RecyclerView.Adapter<OneBrandIDAdapter.BrandIDViewHolder>() {
    private var clicklistener : Clicklistener? = null

    fun setClickListener(clicklistener: Clicklistener){
        this.clicklistener = clicklistener
    }

    inner class BrandIDViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),View.OnClickListener {
        private lateinit var specificate : Specificate
        init {
            itemView.setOnClickListener(this)
        }

        fun bindID(specifications: Specificate) {
            this.specificate = specifications
            itemView.one_name.text = specifications.cname
            Picasso.get().load(specifications.image).placeholder(R.drawable.ic_launcher_background)
                .into(itemView.one_image)
        }

        override fun onClick(v: View?) {
            clicklistener?.onClick(specificate)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BrandIDViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_one_brand, parent, false)
        return BrandIDViewHolder(view)
    }

    override fun getItemCount(): Int {
        return oneList.size
    }

    override fun onBindViewHolder(holder: BrandIDViewHolder, position: Int) {
        holder.bindID(oneList[position])
    }

    fun updateOne(oneList: ArrayList<Specificate>) {
        this.oneList = oneList
        notifyDataSetChanged()
    }

    interface Clicklistener{
        fun onClick(detailBrand : Specificate)
    }
}