package com.dogood.thamizhimemes.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dogood.thamizhimemes.R

class GalleryImageViewAdapter(context:Context,images:List<String>,photoListener: PhotoListener) :RecyclerView.Adapter<GalleryImageViewAdapter.ViewHolder>(){

    var context=context
    var images=images
    var photoListener=photoListener

    class ViewHolder(view:View):RecyclerView.ViewHolder(view){
        var image=itemView.findViewById(R.id.gallery_image) as ImageView
    }

    interface PhotoListener{
        fun onPhotoClick(path:String)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view=LayoutInflater.from(context).inflate(R.layout.gallery_image_item,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return images.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var image=images.get(position)
        Glide.with(context).load(image).into(holder.image)
        holder.itemView.setOnClickListener {
            photoListener.onPhotoClick(image)
        }
    }
}