package com.furkandonertas.idealustam.features.masters.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.furkandonertas.idealustam.features.masters.domain.model.Master
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MastersViewModel : ViewModel() {

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
            // Simüle edilmiş yükleme gecikmesi
            delay(1000)
            
            // Mock veri
            val mockMasters = listOf(
                Master(
                    id = "1",
                    name = "Ahmet Usta",
                    specialty = "Motor Tamiri",
                    rating = 4.5f,
                    experience = 15,
                    location = "İstanbul",
                    imageUrl = null,
                    isFavorite = false
                ),
                Master(
                    id = "2",
                    name = "Mehmet Usta",
                    specialty = "Kaporta Boyama",
                    rating = 4.8f,
                    experience = 20,
                    location = "Ankara",
                    imageUrl = null,
                    isFavorite = true
                ),
                Master(
                    id = "3",
                    name = "Ali Usta",
                    specialty = "Elektrik Sistemleri",
                    rating = 4.3f,
                    experience = 10,
                    location = "İzmir",
                    imageUrl = null,
                    isFavorite = false
                )
            )
            
            _mastersState.value = MastersState.Success(mockMasters)
        }
    }

    fun onMasterSelected(master: Master) {
        // TODO: Implement navigation or detail view
    }

    fun onFavoriteClicked(master: Master) {
        val currentState = _mastersState.value
        if (currentState is MastersState.Success) {
            val updatedMasters = currentState.masters.map {
                if (it.id == master.id) it.copy(isFavorite = !it.isFavorite) else it
            }
            _mastersState.value = MastersState.Success(updatedMasters)
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