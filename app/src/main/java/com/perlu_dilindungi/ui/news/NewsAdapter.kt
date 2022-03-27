package com.perlu_dilindungi.ui.news

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.perlu_dilindungi.data.News
import com.perlu_dilindungi.databinding.NewsItemBinding

/**
 * The adapter for recyclerView in NewsFragment.
 */
class NewsAdapter(
    private val onItemClicked: (News) -> Unit
) : ListAdapter<News, NewsAdapter.NewsViewHolder>(DiffCallback) {

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<News>() {
            override fun areItemsTheSame(oldItem: News, newItem: News): Boolean {
                return oldItem.guid == newItem.guid
            }

            override fun areContentsTheSame(oldItem: News, newItem: News): Boolean {
                return oldItem == newItem
            }
        }
    }

    class NewsViewHolder(
        private var binding: NewsItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(news: News) {
            binding.news = news
            // This is important, because it forces the data binding to execute immediately,
            // which allows the RecyclerView to make the correct view size measurements
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        // Set the viewHolder.
        val viewHolder = NewsViewHolder(
            NewsItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

        // Set onClickListener.
        viewHolder.itemView.setOnClickListener {
            val position = viewHolder.adapterPosition
            onItemClicked(getItem(position))
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        // Bind the item.
        val news = getItem(position)
        holder.bind(news)
    }
}