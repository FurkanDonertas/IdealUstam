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
import com.furkandonertas.idealustam.features.auth.presentation.viewmodel.LoginViewModel
import com.google.android.material.button.MaterialButton
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.google.android.material.textfield.TextInputEditText

class LoginActivity : AppCompatActivity() {

    private val viewModel: LoginViewModel by viewModels()
    
    private lateinit var emailInput: TextInputEditText
    private lateinit var passwordInput: TextInputEditText
    private lateinit var loginButton: MaterialButton
    private lateinit var progressIndicator: CircularProgressIndicator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        setupViews()
        setupListeners()
        observeViewModel()
    }

    private fun setupViews() {
        emailInput = findViewById(R.id.emailInput)
        passwordInput = findViewById(R.id.passwordInput)
        loginButton = findViewById(R.id.loginButton)
        progressIndicator = findViewById(R.id.progressIndicator)
    }

    private fun setupListeners() {
        loginButton.setOnClickListener {
            val email = emailInput.text.toString()
            val password = passwordInput.text.toString()
            viewModel.login(email, password)
        }

        findViewById<TextView>(R.id.signUpButton).setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
            finish()
        }

        findViewById<TextView>(R.id.forgotPasswordText).setOnClickListener {
            startActivity(Intent(this, ForgotPasswordActivity::class.java))
        }
    }

    private fun observeViewModel() {
        viewModel.loginState.observe(this) { state ->
            when (state) {
                LoginViewModel.LoginState.Loading -> {
                    progressIndicator.visibility = View.VISIBLE
                    loginButton.isEnabled = false
                }
                LoginViewModel.LoginState.Success -> {
                    progressIndicator.visibility = View.GONE
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }
                LoginViewModel.LoginState.Error -> {
                    progressIndicator.visibility = View.GONE
                    loginButton.isEnabled = true
                }
                else -> {
                    progressIndicator.visibility = View.GONE
                    loginButton.isEnabled = true
                }
            }
        }

        viewModel.errorMessage.observe(this) { message ->
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }
    }
} 