package com.furkandonertas.idealustam.features.auth.presentation.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.furkandonertas.idealustam.R
import com.furkandonertas.idealustam.features.auth.presentation.viewmodel.ForgotPasswordViewModel
import com.google.android.material.button.MaterialButton
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.google.android.material.textfield.TextInputEditText

class ForgotPasswordActivity : AppCompatActivity() {

    private val viewModel: ForgotPasswordViewModel by viewModels()
    
    private lateinit var emailInput: TextInputEditText
    private lateinit var submitButton: MaterialButton
    private lateinit var progressIndicator: CircularProgressIndicator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        setupViews()
        setupListeners()
        observeViewModel()
    }

    private fun setupViews() {
        emailInput = findViewById(R.id.emailInput)
        submitButton = findViewById(R.id.submitButton)
        progressIndicator = findViewById(R.id.progressIndicator)
    }

    private fun setupListeners() {
        submitButton.setOnClickListener {
            val email = emailInput.text.toString()
            viewModel.submitEmail(email)
        }

        findViewById<TextView>(R.id.loginButton).setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }

    private fun observeViewModel() {
        viewModel.forgotPasswordState.observe(this) { state ->
            when (state) {
                ForgotPasswordViewModel.ForgotPasswordState.Loading -> {
                    progressIndicator.visibility = View.VISIBLE
                    submitButton.isEnabled = false
                }
                ForgotPasswordViewModel.ForgotPasswordState.Success -> {
                    progressIndicator.visibility = View.GONE
                    Toast.makeText(this, "Şifre sıfırlama bağlantısı email adresinize gönderildi", Toast.LENGTH_LONG).show()
                    startActivity(Intent(this, LoginActivity::class.java))
                    finish()
                }
                ForgotPasswordViewModel.ForgotPasswordState.Error -> {
                    progressIndicator.visibility = View.GONE
                    submitButton.isEnabled = true
                }
                else -> {
                    progressIndicator.visibility = View.GONE
                    submitButton.isEnabled = true
                }
            }
        }

        viewModel.errorMessage.observe(this) { message ->
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }
    }
} 