package com.example.a13_179.ui.viewmodel.peserta

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.a13_179.model.Peserta
import com.example.a13_179.repository.PesertaRepository
import com.example.a13_179.ui.view.peserta.DestinasiUpdatePeserta
import kotlinx.coroutines.launch

class UpdatePesertaViewModel(
    savedStateHandle: SavedStateHandle,
    private val repositoryPeserta: PesertaRepository
) : ViewModel() {

    var updatePesertaUiState by mutableStateOf(InsertPesertaUiState())
        private set

    private val _id_peserta: Int =
        checkNotNull(savedStateHandle[DestinasiUpdatePeserta.id_peserta])

    init {
        // Ambil data peserta berdasarkan ID saat ViewModel diinisialisasi
        viewModelScope.launch {
            updatePesertaUiState = repositoryPeserta
                .getPesertaById(_id_peserta)
                .toPesertaUiState()
        }
    }

    // Fungsi untuk memperbarui UI state dengan event baru
    fun updateInsertPesertaState(insertPesertaUiEvent: InsertPesertaUiEvent) {
        updatePesertaUiState = InsertPesertaUiState(insertPesertaUiEvent = insertPesertaUiEvent)
    }

    // Fungsi untuk memperbarui peserta di repository
    fun updatePeserta() {
        viewModelScope.launch {
            try {
                repositoryPeserta.updatePeserta(
                    _id_peserta,
                    updatePesertaUiState.insertPesertaUiEvent.toPsrta()
                )
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
