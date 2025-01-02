package com.furkandonertas.idealustam.features.settings.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.furkandonertas.idealustam.features.settings.domain.model.UserSettings
import com.furkandonertas.idealustam.features.settings.domain.usecase.GetUserSettingsUseCase
import com.furkandonertas.idealustam.features.settings.domain.usecase.UpdateSettingsUseCase
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val getUserSettingsUseCase: GetUserSettingsUseCase,
    private val updateSettingsUseCase: UpdateSettingsUseCase
) : ViewModel() {

    private val _settingsState = MutableLiveData<SettingsState>()
    val settingsState: LiveData<SettingsState> = _settingsState

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    init {
        loadSettings()
    }

    fun loadSettings() {
        _settingsState.value = SettingsState.Loading

        viewModelScope.launch {
            getUserSettingsUseCase()
                .onSuccess { settings ->
                    _settingsState.value = SettingsState.Success(settings)
                }
                .onFailure { exception ->
                    _settingsState.value = SettingsState.Error
                    _errorMessage.value = exception.message ?: "Ayarlar yüklenirken bir hata oluştu"
                }
        }
    }

    fun updateTheme(isDarkTheme: Boolean) {
        viewModelScope.launch {
            updateSettingsUseCase.updateTheme(isDarkTheme)
                .onFailure { exception ->
                    _errorMessage.value = exception.message ?: "Tema güncellenirken bir hata oluştu"
                }
        }
    }

    fun updateNotifications(isEnabled: Boolean) {
        viewModelScope.launch {
            updateSettingsUseCase.updateNotifications(isEnabled)
                .onFailure { exception ->
                    _errorMessage.value = exception.message ?: "Bildirim ayarları güncellenirken bir hata oluştu"
                }
        }
    }

    fun logout() {
        viewModelScope.launch {
            updateSettingsUseCase.logout()
                .onFailure { exception ->
                    _errorMessage.value = exception.message ?: "Çıkış yapılırken bir hata oluştu"
                }
        }
    }

    sealed class SettingsState {
        object Initial : SettingsState()
        object Loading : SettingsState()
        data class Success(val settings: UserSettings) : SettingsState()
        object Error : SettingsState()
    }
} 