package com.blinkllc.movieapp.movieList.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.blinkllc.movieapp.databinding.MovieListItemBinding
import com.blinkllc.movieapp.movieList.data.local.entities.Movie
import kotlin.properties.Delegates

class MovieListAdapter(
    list: List<Movie> = emptyList(),
    private val onItemClick: (Movie, Int) -> Unit

) : RecyclerView.Adapter<MovieListAdapter.ViewHolder>() {

    private var list: List<Movie> by Delegates.observable(list) { _, old, new ->
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
            result.dispatchUpdatesTo(this@MovieListAdapter)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        return ViewHolder(
            MovieListItemBinding.inflate(
                LayoutInflater.from(viewGroup.context),
                viewGroup,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position], onItemClick)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun updateData(newList: List<Movie>) {
        list = newList
        notifyDataSetChanged()
    }

    class ViewHolder(
        private val binding: MovieListItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Movie, onItemClick: (Movie, Int) -> Unit
        ) {
            binding.apply {

                movieTitle.text = movie.title
                root.setOnClickListener {
                    onItemClick(movie, adapterPosition)
                }
            }

        }
    }
}
