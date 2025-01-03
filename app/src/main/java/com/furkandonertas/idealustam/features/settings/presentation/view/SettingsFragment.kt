package com.furkandonertas.idealustam.features.settings.presentation.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.furkandonertas.idealustam.R
import com.furkandonertas.idealustam.features.auth.presentation.view.LoginActivity
import com.google.android.material.button.MaterialButton
import android.widget.LinearLayout

class SettingsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Tema ayarları tıklama
        view.findViewById<LinearLayout>(R.id.themeLayout).setOnClickListener {
            Toast.makeText(context, "Tema ayarları yakında aktif olacak", Toast.LENGTH_SHORT).show()
        }

        // Bildirim ayarları tıklama
        view.findViewById<LinearLayout>(R.id.notificationLayout).setOnClickListener {
            Toast.makeText(context, "Bildirim ayarları yakında aktif olacak", Toast.LENGTH_SHORT).show()
        }

        // Uygulama hakkında tıklama
        view.findViewById<LinearLayout>(R.id.aboutLayout).setOnClickListener {
            Toast.makeText(context, "Uygulama hakkında yakında aktif olacak", Toast.LENGTH_SHORT).show()
        }

        // Çıkış yap butonu
        view.findViewById<MaterialButton>(R.id.logoutButton).setOnClickListener {
            // LoginActivity'ye yönlendir
            startActivity(Intent(requireContext(), LoginActivity::class.java))
            requireActivity().finish()
        }
    }

    companion object {
        fun newInstance() = SettingsFragment()
    }
} 