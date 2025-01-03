package com.furkandonertas.idealustam.features.services.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.furkandonertas.idealustam.R
import com.google.android.material.card.MaterialCardView

class ServicesFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_services, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Kartlara tıklama olaylarını ekle
        view.findViewById<MaterialCardView>(R.id.roadAssistanceCard).setOnClickListener {
            showServiceMessage("Yol Yardım")
        }

        view.findViewById<MaterialCardView>(R.id.selfExpertCard).setOnClickListener {
            showServiceMessage("Self Ekspertiz")
        }

        view.findViewById<MaterialCardView>(R.id.carWashCard).setOnClickListener {
            showServiceMessage("Oto Kuaför")
        }

        view.findViewById<MaterialCardView>(R.id.modificationCard).setOnClickListener {
            showServiceMessage("Modifiye")
        }

        view.findViewById<MaterialCardView>(R.id.inspectionCard).setOnClickListener {
            showServiceMessage("Araç Muayene")
        }

        view.findViewById<MaterialCardView>(R.id.tiresCard).setOnClickListener {
            showServiceMessage("Lastik & Tekerlek")
        }
    }

    private fun showServiceMessage(serviceName: String) {
        Toast.makeText(requireContext(), "$serviceName modülü yakında aktif olacak", Toast.LENGTH_SHORT).show()
    }

    companion object {
        fun newInstance() = ServicesFragment()
    }
} 