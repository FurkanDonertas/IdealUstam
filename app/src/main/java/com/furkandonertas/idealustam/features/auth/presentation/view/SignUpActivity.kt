package com.furkandonertas.idealustam.features.auth.presentation.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.furkandonertas.idealustam.MainActivity
import com.furkandonertas.idealustam.R
import com.furkandonertas.idealustam.features.auth.presentation.viewmodel.SignUpViewModel
import com.google.android.material.button.MaterialButton
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.google.android.material.textfield.TextInputEditText

class SignUpActivity : AppCompatActivity() {

    private val viewModel: SignUpViewModel by viewModels()
    
    private lateinit var emailInput: TextInputEditText
    private lateinit var passwordInput: TextInputEditText
    private lateinit var confirmPasswordInput: TextInputEditText
    private lateinit var createAccountButton: MaterialButton
    private lateinit var progressIndicator: CircularProgressIndicator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        setupViews()
        setupListeners()
        observeViewModel()
    }

    private fun setupViews() {
        emailInput = findViewById(R.id.emailInput)
        passwordInput = findViewById(R.id.passwordInput)
        confirmPasswordInput = findViewById(R.id.confirmPasswordInput)
        createAccountButton = findViewById(R.id.createAccountButton)
        progressIndicator = findViewById(R.id.progressIndicator)
    }

    private fun setupListeners() {
        createAccountButton.setOnClickListener {
            val email = emailInput.text.toString()
            val password = passwordInput.text.toString()
            val confirmPassword = confirmPasswordInput.text.toString()
            viewModel.signUp(email, password, confirmPassword)
        }

        findViewById<TextView>(R.id.loginButton).setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }

    private fun observeViewModel() {
        viewModel.signUpState.observe(this) { state ->
            when (state) {
                SignUpViewModel.SignUpState.Loading -> {
                    progressIndicator.visibility = View.VISIBLE
                    createAccountButton.isEnabled = false
                }
                SignUpViewModel.SignUpState.Success -> {
                    progressIndicator.visibility = View.GONE
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }
                SignUpViewModel.SignUpState.Error -> {
                    progressIndicator.visibility = View.GONE
                    createAccountButton.isEnabled = true
                }
                else -> {
                    progressIndicator.visibility = View.GONE
                    createAccountButton.isEnabled = true
                }
            }
        }

        viewModel.errorMessage.observe(this) { message ->
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }
    }
} 