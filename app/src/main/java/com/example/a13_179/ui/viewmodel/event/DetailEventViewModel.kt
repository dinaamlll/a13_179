package com.example.a13_179.ui.viewmodel.event

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.a13_179.model.Event
import com.example.a13_179.repository.EventRepository
import com.example.a13_179.ui.view.event.DestinasiDetailEvent
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

sealed class DetailEventlUiState {
    data class Success(val event: Event) : DetailEventlUiState()
    object Error : DetailEventlUiState()
    object Loading : DetailEventlUiState()
}

class DetailEventViewModel(
    savedStateHandle: SavedStateHandle,
    private val evnt: EventRepository
) : ViewModel() {

    private val _idEvent: Int = savedStateHandle.get<String>(DestinasiDetailEvent.ID_EVENT)
        ?.toIntOrNull()
        ?: throw IllegalArgumentException("Invalid ID_EVENT value")

    private val _detailEventlUiState = MutableStateFlow<DetailEventlUiState>(DetailEventlUiState.Loading)
    val detailEventlUiState: StateFlow<DetailEventlUiState> = _detailEventlUiState

    init {
        getDetailEvent()
    }

    fun getDetailEvent() {
        viewModelScope.launch {
            try {
                _detailEventlUiState.value = DetailEventlUiState.Loading
                val event = evnt.getEventById(_idEvent)
                _detailEventlUiState.value = if (event != null) {
                    DetailEventlUiState.Success(event)
                } else {
                    DetailEventlUiState.Error
                }
            } catch (e: Exception) {
                _detailEventlUiState.value = DetailEventlUiState.Error
            }
        }
    }
}

fun Event.toDetailEventlUiEvent(): InsertEventUiEvent {
    return InsertEventUiEvent(
        id_event = id_event,
        nama_event = nama_event,
        deskripsi_event = deskripsi_event,
        tanggal_event = tanggal_event,
        lokasi_event = lokasi_event
    )
}
