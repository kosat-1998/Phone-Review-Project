package com.sps.phoneupdatemyanmar.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.sps.phoneupdatemyanmar.R
import com.sps.phoneupdatemyanmar.model_all_brands.Specificate
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_all_brands.view.*
import kotlinx.android.synthetic.main.item_favorite.view.*

class FavoriteListAdapter(var specificate: ArrayList<Specificate> = ArrayList(),var context: Context?) :
    RecyclerView.Adapter<FavoriteListAdapter.FavoriteListViewHolder>() {

    private  var favoriteClick: FavoriteClick? = null
    fun setClickListener(favoriteClick: FavoriteClick){
        this.favoriteClick = favoriteClick
    }
    inner class FavoriteListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),View.OnClickListener {
        private lateinit var specificate: Specificate
        init {
            itemView.setOnClickListener(this)
        }
        fun bindFavorite(specificate: Specificate) {
            this.specificate = specificate
            itemView.favorite_name.text = specificate.category.name
            Picasso.get().load(specificate.image).placeholder(R.drawable.loading)
                .into(itemView.favorite_image)
        }

        override fun onClick(v: View?) {
            favoriteClick?.onClick(specificate)
            Toast.makeText(context, "result", Toast.LENGTH_SHORT).show()
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteListViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_favorite, parent, false)
        return FavoriteListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return specificate.size
    }

    override fun onBindViewHolder(holder: FavoriteListViewHolder, position: Int) {
        holder.bindFavorite(specificate[position])
    }

    interface FavoriteClick{
        fun onClick(specificate: Specificate)
    }
}