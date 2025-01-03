package com.furkandonertas.idealustam

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.furkandonertas.idealustam.features.home.presentation.view.MyCarFragment
import com.furkandonertas.idealustam.features.masters.presentation.view.MastersFragment
import com.furkandonertas.idealustam.features.services.presentation.view.ServicesFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.furkandonertas.idealustam.features.settings.presentation.view.SettingsFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_my_car -> {
                    loadFragment(MyCarFragment())
                    true
                }
                R.id.navigation_services -> {
                    loadFragment(ServicesFragment())
                    true
                }
                R.id.navigation_ustabul -> {
                    // TODO: Usta Bul fragmentı eklenecek
                    true
                }
                R.id.navigation_ustalarim -> {
                    loadFragment(MastersFragment())
                    true
                }
                R.id.navigation_settings -> {
                    loadFragment(SettingsFragment.newInstance())
                    true
                }
                else -> false
            }
        }

        // Başlangıçta Arabam sayfasını göster
        loadFragment(MyCarFragment())
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}