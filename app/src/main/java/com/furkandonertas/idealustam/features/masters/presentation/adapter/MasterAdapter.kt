package com.furkandonertas.idealustam.features.masters.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.furkandonertas.idealustam.R
import com.furkandonertas.idealustam.features.masters.domain.model.Master
import com.google.android.material.card.MaterialCardView

class MasterAdapter(
    private var masters: List<Master>,
    private val onMasterClick: (Master) -> Unit,
    private val onFavoriteClick: (Master) -> Unit
) : RecyclerView.Adapter<MasterAdapter.MasterViewHolder>() {

    inner class MasterViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val masterCard: MaterialCardView = view.findViewById(R.id.masterCard)
        val profileImage: ImageView = view.findViewById(R.id.profileImage)
        val nameText: TextView = view.findViewById(R.id.nameText)
        val specialtyText: TextView = view.findViewById(R.id.specialtyText)
        val ratingText: TextView = view.findViewById(R.id.ratingText)
        val favoriteButton: ImageView = view.findViewById(R.id.favoriteButton)
        val locationText: TextView = view.findViewById(R.id.locationText)
        val experienceText: TextView = view.findViewById(R.id.experienceText)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MasterViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_master, parent, false)
        return MasterViewHolder(view)
    }

    override fun onBindViewHolder(holder: MasterViewHolder, position: Int) {
        val master = masters[position]

        holder.nameText.text = master.name
        holder.specialtyText.text = master.specialty
        holder.ratingText.text = String.format("%.1f", master.rating)
        holder.locationText.text = master.location
        holder.experienceText.text = "${master.experience} yıl deneyim"

        // Profil resmi
        if (master.imageUrl != null) {
            // TODO: Profil resmini yükle (Glide veya Coil kullanılabilir)
        } else {
            holder.profileImage.setImageResource(R.drawable.default_profile)
        }

        // Favori durumuna göre ikonu güncelle
        holder.favoriteButton.setImageResource(
            if (master.isFavorite) R.drawable.ic_favorite_filled
            else R.drawable.ic_favorite_border
        )

        // Click listeners
        holder.masterCard.setOnClickListener { onMasterClick(master) }
        holder.favoriteButton.setOnClickListener { onFavoriteClick(master) }
    }

    override fun getItemCount() = masters.size

    fun updateMasters(newMasters: List<Master>) {
        masters = newMasters
        notifyDataSetChanged()
    }
} 