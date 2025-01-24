package com.example.a13_179.ui.viewmodel.event


import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.a13_179.model.Event
import com.example.a13_179.repository.EventRepository
import com.example.a13_179.ui.view.event.DestinasiUpdateEvent
import kotlinx.coroutines.launch

class UpdateEventViewModel(
    savedStateHandle: SavedStateHandle,
    private val evnt: EventRepository
) : ViewModel() {

    // Retrieve the NIM from SavedStateHandle
    val id_event: Int = checkNotNull(savedStateHandle[DestinasiUpdateEvent.ID_EVENT])

    var EventuiState = mutableStateOf(InsertEventUiState())
        private set

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