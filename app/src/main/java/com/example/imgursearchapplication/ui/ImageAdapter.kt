package com.example.mysearchapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.imgursearchapplication.ui.ImageClickCallback
import com.example.imgursearchapplication.R
import com.example.imgursearchapplication.db.PhotoData
import com.squareup.picasso.Picasso

class ImageAdapter(val callback: ImageClickCallback) : PagedListAdapter<PhotoData, ImageAdapter.ImageViewHolder>(diffCallback) {

    inner class ImageViewHolder(view: View): RecyclerView.ViewHolder(view), View.OnClickListener {
        val imageView = view.findViewById<ImageView>(R.id.image)
        var photoDataObj: PhotoData? = null
        init {
            view.setOnClickListener(this)
        }
        fun bind(photoData: PhotoData?) {
            if(!photoData?.images.isNullOrEmpty() && !photoData?.images?.get(0)?.link.isNullOrEmpty()) {
                Picasso.get().load(photoData?.images?.get(0)?.link).fit().placeholder(R.drawable.gray_background).into(imageView)
                photoDataObj = photoData
            }
        }
        override fun onClick(v: View?) {
           photoDataObj?.let {
               callback.onClick(it)
           } ?: Toast.makeText(v?.context,"Data not available",Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_photo_item, parent, false)
        return ImageViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<PhotoData>() {
            override fun areContentsTheSame(oldItem: PhotoData, newItem: PhotoData): Boolean {
                return  oldItem.id == newItem.id
            }

            override fun areItemsTheSame(oldItem: PhotoData, newItem: PhotoData): Boolean {
                return oldItem == newItem
            }
        }
    }

}