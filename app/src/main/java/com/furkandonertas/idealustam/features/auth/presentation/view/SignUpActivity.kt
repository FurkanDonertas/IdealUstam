package com.furkandonertas.idealustam.features.auth.presentation.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.furkandonertas.idealustam.R
import com.furkandonertas.idealustam.features.auth.presentation.viewmodel.AuthViewModel
import com.google.android.material.button.MaterialButton
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.google.android.material.textfield.TextInputEditText
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SignUpActivity : AppCompatActivity() {

    private val viewModel: AuthViewModel by viewModels()

    private lateinit var emailInput: TextInputEditText
    private lateinit var usernameInput: TextInputEditText
    private lateinit var passwordInput: TextInputEditText
    private lateinit var confirmPasswordInput: TextInputEditText
    private lateinit var createAccountButton: MaterialButton
    private lateinit var progressIndicator: CircularProgressIndicator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        initializeViews()
        setupClickListeners()
        observeViewModel()
    }

    private fun initializeViews() {
        emailInput = findViewById(R.id.emailInput)
        usernameInput = findViewById(R.id.usernameInput)  // Yeni eklendi
        passwordInput = findViewById(R.id.passwordInput)
        confirmPasswordInput = findViewById(R.id.confirmPasswordInput)
        createAccountButton = findViewById(R.id.createAccountButton)
        progressIndicator = findViewById(R.id.progressIndicator)
    }

    private fun setupClickListeners() {
        findViewById<TextView>(R.id.loginButton).setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        createAccountButton.setOnClickListener {
            val email = emailInput.text.toString()
            val username = usernameInput.text.toString()  // Yeni eklendi
            val password = passwordInput.text.toString()
            val confirmPassword = confirmPasswordInput.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty() && confirmPassword.isNotEmpty() && username.isNotEmpty()) {
                if (password == confirmPassword) {
                    progressIndicator.visibility = View.VISIBLE
                    createAccountButton.visibility = View.INVISIBLE
                    viewModel.register(email, password, username)
                } else {
                    Toast.makeText(this, "Şifreler eşleşmiyor", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Lütfen tüm alanları doldurun", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.loginState.collect { state ->
                when(state) {
                    is AuthViewModel.LoginState.Loading -> {
                        progressIndicator.visibility = View.VISIBLE
                        createAccountButton.isEnabled = false
                    }
                    is AuthViewModel.LoginState.Success -> {
                        progressIndicator.visibility = View.GONE
                        createAccountButton.isEnabled = true
                        Toast.makeText(this@SignUpActivity, "Kayıt başarılı! Giriş yapabilirsiniz.", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this@SignUpActivity, LoginActivity::class.java))
                        finish()
                    }
                    is AuthViewModel.LoginState.Error -> {
                        progressIndicator.visibility = View.GONE
                        createAccountButton.isEnabled = true
                        createAccountButton.visibility = View.VISIBLE
                        Toast.makeText(this@SignUpActivity, state.message, Toast.LENGTH_SHORT).show()
                    }
                    else -> Unit
                }
            }
        }
    }
}