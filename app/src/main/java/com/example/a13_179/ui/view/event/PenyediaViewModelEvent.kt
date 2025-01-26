package com.example.a13_179.ui.view.event

import com.example.a13_179.ui.viewmodel.tiket.DetailTiketViewModel
import com.example.a13_179.ui.viewmodel.tiket.HomeTiketViewModel
import com.example.a13_179.ui.viewmodel.tiket.InsertTiketViewModel
import com.example.a13_179.ui.viewmodel.tiket.UpdateTiketViewModel
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.a13_179.EventApplications
import com.example.a13_179.ui.viewmodel.event.DetailEventViewModel
import com.example.a13_179.ui.viewmodel.event.HomeEventViewModel
import com.example.a13_179.ui.viewmodel.event.InsertEventViewModel
import com.example.a13_179.ui.viewmodel.event.UpdateEventViewModel


object PenyediaViewModelEvent {
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