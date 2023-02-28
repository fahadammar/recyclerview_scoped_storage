package com.example.imagesvideosrecycler.view.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.imagesvideosrecycler.R

class ImageAdapter(private val context : Context, private val images: ArrayList<String>) :
    RecyclerView.Adapter<ImageAdapter.ImageViewHolder>() {

    class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.image_item, parent, false)
        return ImageViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        // val imagePath = images[position]
        Glide.with(context)
            .load(images[position])
            .into(holder.imageView)
        //holder.imageView.setImageResource(imagePath.toInt())
    }

    override fun getItemCount(): Int {
        Log.d("buddy", "getItemCount: ${images.size} ")
        return images.size
    }
}
