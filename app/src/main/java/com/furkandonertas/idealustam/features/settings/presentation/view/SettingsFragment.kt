package com.furkandonertas.idealustam.features.settings.presentation.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.furkandonertas.idealustam.R
import com.furkandonertas.idealustam.features.auth.presentation.view.LoginActivity
import com.furkandonertas.idealustam.features.settings.presentation.viewmodel.SettingsViewModel
import com.google.android.material.materialswitch.MaterialSwitch
import com.google.android.material.progressindicator.CircularProgressIndicator

class SettingsFragment : Fragment() {

    private val viewModel: SettingsViewModel by viewModels()
    
    private lateinit var progressIndicator: CircularProgressIndicator
    private lateinit var themeSwitch: MaterialSwitch
    private lateinit var notificationsSwitch: MaterialSwitch

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews(view)
        setupListeners()
        observeViewModel()
    }

    private fun setupViews(view: View) {
        progressIndicator = view.findViewById(R.id.progressIndicator)
        themeSwitch = view.findViewById(R.id.themeSwitch)
        notificationsSwitch = view.findViewById(R.id.notificationsSwitch)
    }

    private fun setupListeners() {
        themeSwitch.setOnCheckedChangeListener { _, isChecked ->
            viewModel.updateTheme(isChecked)
        }

        notificationsSwitch.setOnCheckedChangeListener { _, isChecked ->
            viewModel.updateNotifications(isChecked)
        }

        view?.findViewById<View>(R.id.logoutButton)?.setOnClickListener {
            showLogoutConfirmationDialog()
        }
    }

    private fun observeViewModel() {
        viewModel.settingsState.observe(viewLifecycleOwner) { state ->
            when (state) {
                SettingsViewModel.SettingsState.Loading -> {
                    progressIndicator.visibility = View.VISIBLE
                }
                is SettingsViewModel.SettingsState.Success -> {
                    progressIndicator.visibility = View.GONE
                    themeSwitch.isChecked = state.settings.isDarkTheme
                    notificationsSwitch.isChecked = state.settings.isNotificationsEnabled
                }
                SettingsViewModel.SettingsState.Error -> {
                    progressIndicator.visibility = View.GONE
                }
                else -> {
                    progressIndicator.visibility = View.GONE
                }
            }
        }

        viewModel.errorMessage.observe(viewLifecycleOwner) { message ->
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun showLogoutConfirmationDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle("Çıkış Yap")
            .setMessage("Çıkış yapmak istediğinize emin misiniz?")
            .setPositiveButton("Evet") { _, _ ->
                viewModel.logout()
                navigateToLogin()
            }
            .setNegativeButton("Hayır", null)
            .show()
    }

    private fun navigateToLogin() {
        val intent = Intent(requireContext(), LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        requireActivity().finish()
    }

    companion object {
        fun newInstance() = SettingsFragment()
    }
} 