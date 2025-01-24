package com.example.a13_179.ui.viewmodel.event

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.a13_179.model.Event
import com.example.a13_179.repository.EventRepository
import com.example.a13_179.ui.view.event.DestinasiDetailEvent
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

sealed class DetaiEventlUiState {
    data class Success(val event: Event) : DetaiEventlUiState()
    object Error : DetaiEventlUiState()
    object Loading : DetaiEventlUiState()
}

class DetailEventViewModel(
    savedStateHandle: SavedStateHandle,
    private val evnt: EventRepository
) : ViewModel() {

    private val _idEvent: Int = checkNotNull(savedStateHandle[DestinasiDetailEvent.ID_EVENT])

    // StateFlow untuk menyimpan status UI
    private val _detaiEventlUiState = MutableStateFlow<DetaiEventlUiState>(DetaiEventlUiState.Loading)
    val detaiEventlUiState: StateFlow<DetaiEventlUiState> = _detaiEventlUiState

    init {
        getDetailEvent()
    }

    fun getDetailEvent() {
        viewModelScope.launch {
            try {
                // Set loading state
                _detaiEventlUiState.value = DetaiEventlUiState.Loading

                // Fetch mahasiswa data dari repository
                val event = evnt.getEventById(_idEvent)

                if (event != null) {
                    // Jika data ditemukan, emit sukses
                    _detaiEventlUiState.value = DetaiEventlUiState.Success(event)
                } else {
                    // Jika data tidak ditemukan, emit error
                    _detaiEventlUiState.value = DetaiEventlUiState.Error
                }
            } catch (e: Exception) {
                // Emit error jika terjadi exception
                _detaiEventlUiState.value = DetaiEventlUiState.Error
            }
        }
    }
}

//memindahkan data dari entity ke ui
fun Event.toDetaiEventlUiEvent(): InsertEventUiEvent {
    return InsertEventUiEvent(
        id_event = id_event,
        nama_event = nama_event,
        deskripsi_event = deskripsi_event,
        tanggal_event = tanggal_event,
        lokasi_event = lokasi_event

    )
}