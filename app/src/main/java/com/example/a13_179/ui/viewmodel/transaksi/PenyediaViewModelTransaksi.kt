package com.example.a13_179.ui.viewmodel.tiket

import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.a13_179.EventApplications
import com.example.a13_179.ui.viewmodel.transaksi.DetailTransaksiViewModel
import com.example.a13_179.ui.viewmodel.transaksi.HomeTransaksiViewModel
import com.example.a13_179.ui.viewmodel.transaksi.InsertTransaksiViewModel


object PenyediaViewModelTransaksi {
    val Factory = viewModelFactory {
        initializer { HomeTransaksiViewModel(AplikasiEvent().container.transaksiRepository) }

        initializer { InsertTransaksiViewModel(AplikasiEvent().container.transaksiRepository) }
        initializer {
            DetailTransaksiViewModel(
                createSavedStateHandle(),
                AplikasiEvent().container.transaksiRepository
            )
        }
    }

    fun CreationExtras.AplikasiEvent(): EventApplications =
        (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as EventApplications)
}