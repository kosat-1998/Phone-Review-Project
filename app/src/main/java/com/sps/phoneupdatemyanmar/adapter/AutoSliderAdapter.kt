package com.sps.phoneupdatemyanmar.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.sps.phoneupdatemyanmar.R
import com.sps.phoneupdatemyanmar.model_all_brands.Specificate
import com.smarteist.autoimageslider.SliderViewAdapter
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_image_slider.view.*
import java.util.*
import kotlin.collections.ArrayList

class AutoSliderAdapter(
    var sliderItem: ArrayList<Specificate> = ArrayList(),
    var context: Context?
) :
    SliderViewAdapter<AutoSliderAdapter.SliderViewHolder>(), View.OnClickListener {

    private lateinit var specificate: Specificate
    private var imageSetOnClickListener: ImageSetOnClickListener? = null

    fun setImageClickListener(imageSetOnClickListener: ImageSetOnClickListener) {
        this.imageSetOnClickListener = imageSetOnClickListener
    }

    inner class SliderViewHolder(itemView: View) : SliderViewAdapter.ViewHolder(itemView) {

        init {
            itemView.setOnClickListener {
                Toast.makeText(context, "result", Toast.LENGTH_SHORT).show()
            }
        }

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
        return 10
    }

    override fun onBindViewHolder(viewHolder: SliderViewHolder?, position: Int) {
        viewHolder?.bindImage(sliderItem[position])
        if (position == 0){
            this.specificate = sliderItem[position]
        }else{
            this.specificate = sliderItem[position-1]
        }

        viewHolder?.itemView?.setOnClickListener(this)
    }

    interface ImageSetOnClickListener {
        fun onclick(specificate: Specificate)
    }

    override fun onClick(v: View?) {
        imageSetOnClickListener?.onclick(specificate)
        //   Toast.makeText(context, "result", Toast.LENGTH_SHORT).show()
    }


}