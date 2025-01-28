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
        ambilEvent()
    }

    // Fetch the student data using NIM
    private fun ambilEvent() {
        viewModelScope.launch {
            try {
                val event = evnt.getEventById(id_event)
                event?.let {
                    EventuiState.value = it.toInsertUIEvent() // Update state with the fetched data
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    // Update the mahasiswa information
    fun updateEvents(id_event: Int, event: Event) {
        viewModelScope.launch {
            try {
                evnt.updateEvent(id_event, event)


            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    // Update the UI state with a new InsertUiEvent
    fun updateEventState(insertEventUiEvent: InsertEventUiEvent) {
        EventuiState.value = EventuiState.value.copy(insertEventUiEvent = insertEventUiEvent)
    }
}

fun Event.toInsertUIEvent(): InsertEventUiState = InsertEventUiState(
    insertEventUiEvent = this.toDetaiEventlUiEvent()
)