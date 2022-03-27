package com.perlu_dilindungi.ui.healthCenter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.perlu_dilindungi.data.HealthCenter
import com.perlu_dilindungi.databinding.HealthCenterItemBinding




/**
 * The adapter for recyclerView in NewsFragment.
 */
class HealthCenterAdapter(
    private val onItemClicked: (HealthCenter) -> Unit
) : ListAdapter<HealthCenter, HealthCenterAdapter.HealthCenterViewHolder>(DiffCallback) {

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<HealthCenter>() {
            override fun areItemsTheSame(oldItem: HealthCenter, newItem: HealthCenter): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: HealthCenter, newItem: HealthCenter): Boolean {
                return oldItem == newItem
            }
        }
    }

    class HealthCenterViewHolder(
        private var binding: HealthCenterItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        // Bind the healthCenter viewModel to data in layout file.
        fun bind(healthCenter: HealthCenter) {
            binding.healthCenter = healthCenter
            binding.executePendingBindings()

            // Check if Jenis Faskes is empty.
            if (binding.type.text.isBlank()) {
                binding.type.text = "---"
//                binding.type.visibility = View.GONE
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HealthCenterViewHolder {
        // Set the viewHolder.
        val viewHolder = HealthCenterViewHolder(
            HealthCenterItemBinding.inflate(
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

    override fun onBindViewHolder(holder: HealthCenterViewHolder, position: Int) {
        // Bind the item.
        val healthCenter = getItem(position)
        holder.bind(healthCenter)
    }
}