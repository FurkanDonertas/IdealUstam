package com.furkandonertas.idealustam.features.auth.presentation.view

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.furkandonertas.idealustam.MainActivity
import com.furkandonertas.idealustam.R
import com.furkandonertas.idealustam.features.auth.presentation.viewmodel.AuthViewModel
import com.furkandonertas.idealustam.features.auth.presentation.viewmodel.AuthViewModel.LoginState
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import androidx.activity.viewModels

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    
    private val viewModel: AuthViewModel by viewModels()
    
    private lateinit var emailInput: TextInputEditText
    private lateinit var passwordInput: TextInputEditText
    private lateinit var loginButton: MaterialButton
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        
        initializeViews()
        setupClickListeners()
        observeViewModel()
    }

    private fun initializeViews() {
        emailInput = findViewById(R.id.emailInput)
        passwordInput = findViewById(R.id.passwordInput)
        loginButton = findViewById(R.id.loginButton)
    }

    private fun setupClickListeners() {
        findViewById<TextView>(R.id.signUpButton).setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            })
        }

        findViewById<TextView>(R.id.forgotPasswordText).setOnClickListener {
            startActivity(Intent(this, ForgotPasswordActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            })
        }

        findViewById<Button>(R.id.loginButton).setOnClickListener {
            val email = emailInput.text.toString()
            val password = passwordInput.text.toString()
            
            if (email.isNotEmpty() && password.isNotEmpty()) {
                viewModel.login(email, password)
            } else {
                Toast.makeText(this, "Lütfen tüm alanları doldurun", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.loginState.collect { state ->
                when(state) {
                    is LoginState.Loading -> {
                        findViewById<Button>(R.id.loginButton).isEnabled = false
                    }
                    is LoginState.Success -> {
                        findViewById<Button>(R.id.loginButton).isEnabled = true
                        startActivity(Intent(this@LoginActivity, MainActivity::class.java).apply {
                            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        })
                        finish()
                    }
                    is LoginState.Error -> {
                        findViewById<Button>(R.id.loginButton).isEnabled = true
                        Toast.makeText(this@LoginActivity, state.message, Toast.LENGTH_SHORT).show()
                    }
                    else -> Unit
                }
            }
        }
    }
} 