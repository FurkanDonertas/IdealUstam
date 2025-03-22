package com.furkandonertas.idealustam.features.auth.presentation.view

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.furkandonertas.idealustam.R
import com.furkandonertas.idealustam.features.auth.presentation.viewmodel.AuthViewModel
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class VerifyActivity : AppCompatActivity() {
    private val viewModel: AuthViewModel by viewModels()
    private lateinit var codeInput: TextInputEditText
    private lateinit var verifyButton: MaterialButton
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verify)
        
        val email = intent.getStringExtra("email") ?: run {
            Toast.makeText(this, "Email bilgisi bulunamadı", Toast.LENGTH_SHORT).show()
            finish()
            return
        }
        
        initializeViews()
        setupClickListeners(email)
        observeViewModel()
    }
    
    private fun initializeViews() {
        codeInput = findViewById(R.id.codeInput)
        verifyButton = findViewById(R.id.verifyButton)
    }
    
    private fun setupClickListeners(email: String) {
        verifyButton.setOnClickListener {
            val code = codeInput.text?.toString()?.trim() ?: ""
            if (code.isNotEmpty()) {
                viewModel.verifyEmail(email, code)
            } else {
                Toast.makeText(this, "Lütfen doğrulama kodunu girin", Toast.LENGTH_SHORT).show()
            }
        }
        
        findViewById<TextView>(R.id.resendButton).setOnClickListener {
            viewModel.resendVerificationCode(email)
            Toast.makeText(this, "Yeni kod gönderiliyor...", Toast.LENGTH_SHORT).show()
        }
    }
    
    private fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.verificationState.collect { state ->
                when (state) {
                    is AuthViewModel.VerificationState.Success -> {
                        Toast.makeText(this@VerifyActivity, "Email doğrulandı", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this@VerifyActivity, LoginActivity::class.java).apply {
                            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                            putExtra("email", intent.getStringExtra("email"))
                        })
                        finish()
                    }
                    is AuthViewModel.VerificationState.Error -> {
                        Toast.makeText(this@VerifyActivity, state.message, Toast.LENGTH_SHORT).show()
                    }
                    is AuthViewModel.VerificationState.Loading -> {
                        // Loading durumunu göster (isteğe bağlı)
                    }
                    else -> Unit
                }
            }
        }
    }
}