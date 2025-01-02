package com.furkandonertas.idealustam.features.onboarding.masters.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.furkandonertas.idealustam.databinding.ItemMasterBinding
import com.furkandonertas.idealustam.features.onboarding.masters.domain.model.Master

class MasterAdapter(
    private val onMasterClick: (Master) -> Unit,
    private val onFavoriteClick: (Master) -> Unit
) : ListAdapter<Master, MasterAdapter.MasterViewHolder>(MasterDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MasterViewHolder {
        val binding = ItemMasterBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MasterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MasterViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class MasterViewHolder(
        private val binding: ItemMasterBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onMasterClick(getItem(position))
                }
            }

            binding.btnFavorite.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onFavoriteClick(getItem(position))
                }
            }
        }

        fun bind(master: Master) {
            binding.apply {
                tvName.text = master.name
                tvSpecialty.text = master.specialty
                ratingBar.rating = master.rating
                tvLastMessage.text = master.lastMessage
                tvLastMessageTime.text = master.lastMessageTime
                btnFavorite.isSelected = master.isFavorite
            }
        }
    }

    private class MasterDiffCallback : DiffUtil.ItemCallback<Master>() {
        override fun areItemsTheSame(oldItem: Master, newItem: Master): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Master, newItem: Master): Boolean {
            return oldItem == newItem
        }
    }
} 