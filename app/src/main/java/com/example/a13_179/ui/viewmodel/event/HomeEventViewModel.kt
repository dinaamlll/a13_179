package com.example.a13_179.ui.viewmodel.event

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.example.a13_179.model.Event
import com.example.a13_179.repository.EventRepository
import kotlinx.coroutines.launch
import okio.IOException

sealed class HomeEventUiState {
    data class Success(val event: List<Event>) : HomeEventUiState()
    object Error : HomeEventUiState()
    object Loading : HomeEventUiState()
}

class HomeEventViewModel(private val evnt: EventRepository) : ViewModel() {
    var evntUiState: HomeEventUiState by mutableStateOf(HomeEventUiState.Loading)
        private set

    init {
        getEvnt()
    }

    // Fungsi untuk mengambil event
    fun getEvnt() {
        viewModelScope.launch {
            evntUiState = HomeEventUiState.Loading
            evntUiState = try {
                HomeEventUiState.Success(evnt.getAllEvent())
            } catch (e: IOException) {
                HomeEventUiState.Error
            } catch (e: HttpException) {
                HomeEventUiState.Error
            }
        }
    }

    // Fungsi untuk menghapus event dengan id_event bertipe Int
    fun deleteEvnt(id_event: Int) {
        viewModelScope.launch {
            try {
                // Pastikan parameter id_event yang dikirim sesuai dengan tipe data yang diharapkan di repository
                evnt.deleteEvent(id_event)
                getEvnt()
            } catch (e: IOException) {
                evntUiState = HomeEventUiState.Error
            } catch (e: HttpException) {
                evntUiState = HomeEventUiState.Error
            }
        }
    }
}
