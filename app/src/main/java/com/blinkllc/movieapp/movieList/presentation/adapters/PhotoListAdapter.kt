package com.blinkllc.movieapp.movieList.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.blinkllc.movieapp.databinding.MovieListItemBinding
import com.blinkllc.movieapp.databinding.PhotoListItemBinding
import com.blinkllc.movieapp.movieList.data.local.entities.Movie
import com.blinkllc.movieapp.movieList.domain.model.Photo
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import kotlin.properties.Delegates

class PhotoListAdapter(
    list: List<Photo> = emptyList()

) : RecyclerView.Adapter<PhotoListAdapter.ViewHolder>() {

    private var list: List<Photo> by Delegates.observable(list) { _, old, new ->
        DiffUtil.calculateDiff(
            object : DiffUtil.Callback() {
                override fun getOldListSize() = old.size

                override fun getNewListSize() = new.size

                override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
                    old[oldItemPosition].id == new[newItemPosition].id

                override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
                    old[oldItemPosition] == new[newItemPosition]

            }
        ).also { result ->
            result.dispatchUpdatesTo(this@PhotoListAdapter)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        return ViewHolder(
            PhotoListItemBinding.inflate(
                LayoutInflater.from(viewGroup.context),
                viewGroup,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun updateData(newList: List<Photo>) {
        list = newList
    }

    class ViewHolder(
        private val binding: PhotoListItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(photo: Photo) {
            binding.apply {
                val imageUrl = "https://farm${photo.farm}.static.flickr.com/${photo.server}/${photo.id}_${photo.secret}.jpg"
                fetchImageWithGlide(imageUrl,movieImg)
            }

        }
        private fun fetchImageWithGlide(imageUrl:String,movieImg: ImageView){
            // Load image into an ImageView
            Glide.with(movieImg.context)
                .load(imageUrl)
                .diskCacheStrategy(DiskCacheStrategy.ALL) // Optional: Cache image
                .into(movieImg)

        }
    }
}
