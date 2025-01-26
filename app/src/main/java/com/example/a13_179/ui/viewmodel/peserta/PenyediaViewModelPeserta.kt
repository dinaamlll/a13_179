package com.example.a13_179.ui.viewmodel.peserta

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



object PenyediaViewModelPeserta {
    val Factory = viewModelFactory {
        initializer { HomePesertaViewModel(AplikasiEvent().container.pesertaRepository) }

        initializer { InsertPesertaViewModel(AplikasiEvent().container.pesertaRepository) }
        initializer {
            DetailPesertaViewModel(
                createSavedStateHandle(),
                AplikasiEvent().container.pesertaRepository
            )
        }
        initializer {
            UpdatePesertaViewModel(
                createSavedStateHandle(),
                AplikasiEvent().container.pesertaRepository
            )
        }
    }

    fun CreationExtras.AplikasiEvent(): EventApplications =
        (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as EventApplications)
}