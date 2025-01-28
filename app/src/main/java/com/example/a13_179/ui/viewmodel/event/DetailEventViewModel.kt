package com.example.a13_179.ui.viewmodel.event

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.a13_179.model.Event
import com.example.a13_179.repository.EventRepository
import com.example.a13_179.repository.PesertaRepository
import com.example.a13_179.ui.view.event.DestinasiDetailEvent
import com.example.a13_179.ui.view.event.DestinasiDetailEvent.id_event
import com.example.a13_179.ui.viewmodel.peserta.DetailPesertaUiState
import com.example.a13_179.ui.viewmodel.peserta.InsertPesertaUiEvent
import com.example.a13_179.ui.viewmodel.peserta.toDetailPesertaUiEvent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

    class DetailEventViewModel(
        private val eventRepository: EventRepository
) : ViewModel() {

        var uiState by mutableStateOf(DetailEventUiState())
            private set

        fun getDetailEvent(id_event: Int) {
            viewModelScope.launch {
                uiState = DetailEventUiState(isLoading = true)
                try {
                    val event = eventRepository.getEventById(id_event)

                    // Mengubah state dengan data event yang diterima
                    uiState = DetailEventUiState(detailEventUiEvent = event.toDetailEventUiEvent())
                } catch (e: Exception) {
                    e.printStackTrace()

                    // Menangani error jika request gagal
                    uiState = DetailEventUiState(
                        isError = true,
                        errorMessage = "Failed to fetch details: ${e.message}"
                    )
                }
            }
        }
    }

    data class DetailEventUiState(
        val detailEventUiEvent: InsertEventUiEvent = InsertEventUiEvent(),
        val isLoading: Boolean = false,
        val isError: Boolean = false,
        val errorMessage: String = "",
    ) {
        val isUiEventNotEmpty: Boolean
            get() = detailEventUiEvent != InsertEventUiEvent()
    }

fun Event.toDetailEventUiEvent(): InsertEventUiEvent {
    return InsertEventUiEvent(
        id_event = id_event,
        nama_event = nama_event,
        deskripsi_event = deskripsi_event,
        tanggal_event = tanggal_event,
        lokasi_event = lokasi_event
    )
}
