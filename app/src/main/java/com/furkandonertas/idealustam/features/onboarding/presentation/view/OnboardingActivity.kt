package com.furkandonertas.idealustam.features.onboarding.presentation.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.furkandonertas.idealustam.R
import com.furkandonertas.idealustam.features.auth.presentation.view.LoginActivity
import com.furkandonertas.idealustam.features.onboarding.domain.model.OnboardingItem
import com.furkandonertas.idealustam.features.onboarding.presentation.adapter.OnboardingAdapter
import com.furkandonertas.idealustam.features.onboarding.presentation.viewmodel.OnboardingViewModel
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator

class OnboardingActivity : AppCompatActivity() {
    private val viewModel: OnboardingViewModel by viewModels()
    private lateinit var signInButton: Button
    private lateinit var signUpButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding)

        setupUI()
        setupObservers()
    }

    private fun setupUI() {
        val onboardingItems = listOf(
            OnboardingItem(R.drawable.onboarding_image1, "Arızanı Tanı ve Uygun Ustayı Bul!", "Aracındaki arıza ışığını tanımıyor musun? Sorun değil! İdeal Ustam ile arıza işaretlerinin anlamını öğren ve ihtiyacın olan doğru ustayı hemen bul."),
            OnboardingItem(R.drawable.onboarding_image2, "En Yakın ve Güvenilir Ustayı Önerir.", "Google Reviews ve AI destekli önerilerimizle en yakın ve güvenilir ustaya kolayca ulaş! Aracın için en iyi hizmeti bulmak artık çok kolay."),
            OnboardingItem(R.drawable.onboarding_image3, "Acil Durumlarda Yanındayız!", "Yolda kaldığında endişelenme! Acil durumlarda sana en yakın çekiciyi veya ustayı buluyoruz. Hızlıca yardım al, yoluna devam et.")
        )

        val adapter = OnboardingAdapter(onboardingItems)
        val viewPager = findViewById<ViewPager2>(R.id.viewPager)
        val dotsIndicator = findViewById<DotsIndicator>(R.id.dotsIndicator)
        
        viewPager.adapter = adapter
        dotsIndicator.attachTo(viewPager)

        signInButton = findViewById(R.id.sign_in_button)
        signUpButton = findViewById(R.id.sign_up_button)

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                viewModel.onPageChanged(position, onboardingItems.size)
            }
        })

        signInButton.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        signUpButton.setOnClickListener {
            // TODO: SignUp Activity'ye yönlendirme yapılacak
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }

    private fun setupObservers() {
        viewModel.isLastPage.observe(this) { isLastPage ->
            signInButton.visibility = if (isLastPage) View.VISIBLE else View.GONE
            signUpButton.visibility = if (isLastPage) View.VISIBLE else View.GONE
        }
    }
} 