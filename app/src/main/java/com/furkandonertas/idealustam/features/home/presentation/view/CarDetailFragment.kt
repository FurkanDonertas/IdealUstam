package com.furkandonertas.idealustam.features.home.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.furkandonertas.idealustam.R

class CarDetailFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_car_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        // Mockup değerleri ayarla
        view.findViewById<TextView>(R.id.transmissionText).text = "Auto"
        view.findViewById<TextView>(R.id.horsepowerText).text = "109 HP"
        view.findViewById<TextView>(R.id.engineText).text = "1.5L"
        view.findViewById<TextView>(R.id.speedText).text = "200 km/h"
        view.findViewById<TextView>(R.id.torqueText).text = "250 Nm"
        view.findViewById<TextView>(R.id.mileageText).text = "128.000 km"
        view.findViewById<TextView>(R.id.fuelText).text = "Diesel"
        view.findViewById<TextView>(R.id.cylindersText).text = "4 Cylinders"
    }

    companion object {
        fun newInstance() = CarDetailFragment()
    }
} 