package com.example.a13_179.ui.viewmodel.event

import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.a13_179.EventApplications


object PenyediaViewModel {
    val Factory = viewModelFactory {
        initializer { HomeEventViewModel(AplikasiEvent().container.eventRepository) }
        initializer { InsertEventViewModel(AplikasiEvent().container.eventRepository) }
        initializer {
            DetailEventViewModel(
                createSavedStateHandle(),
                AplikasiEvent().container.eventRepository
            )
        }
        initializer {
            UpdateEventViewModel(
                createSavedStateHandle(),
                AplikasiEvent().container.eventRepository
            )
        }
    }

    fun CreationExtras.AplikasiEvent(): EventApplications =
        (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as EventApplications)
}