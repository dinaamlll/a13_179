package com.example.a13_179.ui.viewmodel.event


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.a13_179.model.Event
import com.example.a13_179.repository.EventRepository
import kotlinx.coroutines.launch

class InsertEventViewModel(private val eventRepository: EventRepository) : ViewModel() {
    var uiState by mutableStateOf(InsertEventUiState())
        private set
    fun updateInsertEventState(insertEventUiEvent: InsertEventUiEvent) {
        uiState = InsertEventUiState(insertEventUiEvent = insertEventUiEvent)
    }

   suspend fun insertEvent() {
        viewModelScope.launch {
            try {
                eventRepository.insertEvent(uiState.insertEventUiEvent.toEvent())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}

data class InsertEventUiState(
    val insertEventUiEvent: InsertEventUiEvent = InsertEventUiEvent()
)

data class InsertEventUiEvent(
    val id_event: Int = 0, // Changed to Int
    val nama_event: String = "",
    val deskripsi_event: String = "",
    val tanggal_event: String = "",
    val lokasi_event: String = "",
)

fun InsertEventUiEvent.toEvent(): Event = Event(
    id_event = id_event,
    nama_event = nama_event,
    deskripsi_event = deskripsi_event,
    tanggal_event = tanggal_event,
    lokasi_event = lokasi_event
)

fun Event.toEventUiState(): InsertEventUiState = InsertEventUiState(
    insertEventUiEvent = toInsertEventUiEvent()
)

fun Event.toInsertEventUiEvent(): InsertEventUiEvent = InsertEventUiEvent(
    id_event = id_event, //int
    nama_event = nama_event,
    deskripsi_event = deskripsi_event,
    tanggal_event = tanggal_event,
    lokasi_event = lokasi_event
)