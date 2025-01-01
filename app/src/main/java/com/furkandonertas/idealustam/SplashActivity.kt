package com.furkandonertas.idealustam

import android.content.Intent
import android.os.Bundle
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.furkandonertas.idealustam.databinding.ActivitySplashBinding


class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // View Binding
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Animasyonu Başlat
        val rotateAnimation = AnimationUtils.loadAnimation(this, R.anim.rotate_animation)
        binding.logoImageView.startAnimation(rotateAnimation)

        // Splash ekranı 3 saniye göster ve ardından OnboardingActivity'ye yönlendir
        binding.logoImageView.postDelayed({
            val intent = Intent(this, OnboardingActivity::class.java)
            startActivity(intent)
            finish()
        }, 3000) // 3 saniye
    }
}