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
    data class EventDetail(val event: Event) : HomeEventUiState()
    object Error : HomeEventUiState()
    object Loading : HomeEventUiState()
}

class HomeEventViewModel(private val evnt: EventRepository) : ViewModel() {
    var eventUIState: HomeEventUiState by mutableStateOf(HomeEventUiState.Loading)
        private set

    init {
        getEventList()
    }

    // Get list of events
    fun getEventList() {
        viewModelScope.launch {
            eventUIState = HomeEventUiState.Loading
            eventUIState = try {
                HomeEventUiState.Success(evnt.getAllEvent())
            } catch (e: IOException) {
                HomeEventUiState.Error
            } catch (e: HttpException) {
                HomeEventUiState.Error
            }
        }
    }

    // Get event detail by ID
    fun getEventDetail(id_event: Int) {
        viewModelScope.launch {
            eventUIState = HomeEventUiState.Loading
            eventUIState = try {
                val event = evnt.getEventById(id_event)
                HomeEventUiState.EventDetail(event)
            } catch (e: IOException) {
                HomeEventUiState.Error
            } catch (e: HttpException) {
                HomeEventUiState.Error
            }
        }
    }

    // Insert a new event
    fun insertEvent(event: Event) {
        viewModelScope.launch {
            try {
                evnt.insertEvent(event)
                getEventList() // Refresh list after adding
            } catch (e: IOException) {
                eventUIState = HomeEventUiState.Error
            } catch (e: HttpException) {
                eventUIState = HomeEventUiState.Error
            }
        }
    }

    // Update an existing event
    fun updateEvents(event: Event) {
        viewModelScope.launch {
            try {
                evnt.updateEvent(event.id_event, event)
                getEventList() // Refresh list after editing
            } catch (e: IOException) {
                eventUIState = HomeEventUiState.Error
            } catch (e: HttpException) {
                eventUIState = HomeEventUiState.Error
            }
        }
    }

    // Delete an event by ID
    fun deleteEvents(id_event: Int) {
        viewModelScope.launch {
            try {
                evnt.deleteEvent(id_event)
                getEventList() // Refresh list after deletion
            } catch (e: IOException) {
                eventUIState = HomeEventUiState.Error
            } catch (e: HttpException) {
                eventUIState = HomeEventUiState.Error
            }
        }
    }
}
