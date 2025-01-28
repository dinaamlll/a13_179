package com.example.a13_179.ui.viewmodel.event


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.a13_179.model.Peserta
import com.example.a13_179.repository.EventRepository
import com.example.a13_179.repository.PesertaRepository
import com.example.a13_179.ui.view.event.DestinasiUpdateEvent
import com.example.a13_179.ui.view.peserta.DestinasiUpdatePeserta
import com.example.a13_179.ui.viewmodel.peserta.InsertPesertaUiEvent
import com.example.a13_179.ui.viewmodel.peserta.InsertPesertaUiState
import com.example.a13_179.ui.viewmodel.peserta.toPesertaUiState
import com.example.a13_179.ui.viewmodel.peserta.toPsrta
import kotlinx.coroutines.launch

class UpdateEventViewModel(
    savedStateHandle: SavedStateHandle,
    private val repositoryEvent: EventRepository
) : ViewModel() {

    var updateEventUiState by mutableStateOf(InsertEventUiState())
        private set

    private val _id_event: Int =
        checkNotNull(savedStateHandle[DestinasiUpdateEvent.id_event])

    init {
        // Ambil data peserta berdasarkan ID saat ViewModel diinisialisasi
        viewModelScope.launch {
            updateEventUiState = repositoryEvent
                .getEventById(_id_event).toEventUiState()
        }
    }

    // Fungsi untuk memperbarui UI state dengan event baru
    fun updateInsertEventState(insertEventUiEvent: InsertEventUiEvent) {
        updateEventUiState = InsertEventUiState(insertEventUiEvent = insertEventUiEvent)
    }

    // Fungsi untuk memperbarui peserta di repository
    fun updateEvent() {
        viewModelScope.launch {
            try {
                repositoryEvent.updateEvent(
                    _id_event,
                    updateEventUiState.insertEventUiEvent.toEvent()
                )
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
