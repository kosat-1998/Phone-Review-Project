package com.sps.phoneupdatemyanmar.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sps.phoneupdatemyanmar.R
import com.sps.phoneupdatemyanmar.model_all_brands.Specificate
import com.smarteist.autoimageslider.SliderViewAdapter
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_image_slider.view.*

class AutoSliderAdapter(var sliderItem: ArrayList<Specificate> ) :
    SliderViewAdapter<AutoSliderAdapter.SliderViewHolder>() {
    inner class SliderViewHolder(itemView: View) : SliderViewAdapter.ViewHolder(itemView) {

        fun bindImage(image: Specificate) {
            Picasso.get().load(image.image)
                .placeholder(R.drawable.loading)
                .into(itemView.image_slide)
            itemView.slide_name.text = image.category.name
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup?): SliderViewHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.item_image_slider, null)
        return SliderViewHolder(view)
    }

    override fun getCount(): Int {
        return sliderItem.size
    }

    override fun onBindViewHolder(viewHolder: SliderViewHolder?, position: Int) {
        viewHolder?.bindImage(sliderItem[position])
    }

//    fun updateItem(sliderItem: ArrayList<Specificate>) {
//        this.sliderItem = sliderItem
//        notifyDataSetChanged()
//    }
}