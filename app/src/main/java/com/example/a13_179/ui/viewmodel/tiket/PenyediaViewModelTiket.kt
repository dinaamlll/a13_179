package com.example.a13_179.ui.viewmodel.tiket

import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.a13_179.EventApplications



object PenyediaViewModelTiket {
    val Factory = viewModelFactory {
        initializer { HomeTiketViewModel(AplikasiEvent().container.tiketRepository) }

        initializer { InsertTiketViewModel(AplikasiEvent().container.tiketRepository) }
        initializer {
            DetailTiketViewModel(
                createSavedStateHandle(),
                AplikasiEvent().container.tiketRepository
            )
        }
        initializer {
            UpdateTiketViewModel(
                createSavedStateHandle(),
                AplikasiEvent().container.tiketRepository
            )
        }
    }

    fun CreationExtras.AplikasiEvent(): EventApplications =
        (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as EventApplications)
}