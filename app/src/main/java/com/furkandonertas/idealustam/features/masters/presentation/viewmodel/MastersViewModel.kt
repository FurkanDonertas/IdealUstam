package com.furkandonertas.idealustam.features.masters.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.furkandonertas.idealustam.features.masters.domain.model.Master
import com.furkandonertas.idealustam.features.masters.domain.usecase.GetMastersUseCase
import kotlinx.coroutines.launch

class MastersViewModel(private val getMastersUseCase: GetMastersUseCase) : ViewModel() {

    private val _mastersState = MutableLiveData<MastersState>()
    val mastersState: LiveData<MastersState> = _mastersState

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    init {
        loadMasters()
    }

    fun loadMasters() {
        _mastersState.value = MastersState.Loading

        viewModelScope.launch {
            getMastersUseCase()
                .onSuccess { masters ->
                    _mastersState.value = if (masters.isEmpty()) {
                        MastersState.Empty
                    } else {
                        MastersState.Success(masters)
                    }
                }
                .onFailure { exception ->
                    _mastersState.value = MastersState.Error
                    _errorMessage.value = exception.message ?: "Ustalar yüklenirken bir hata oluştu"
                }
        }
    }

    sealed class MastersState {
        object Initial : MastersState()
        object Loading : MastersState()
        object Empty : MastersState()
        data class Success(val masters: List<Master>) : MastersState()
        object Error : MastersState()
    }
} 