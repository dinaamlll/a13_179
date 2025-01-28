package com.example.a13_179.ui.viewmodel.peserta

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.a13_179.model.Event
import com.example.a13_179.model.Peserta
import com.example.a13_179.repository.PesertaRepository
import com.example.a13_179.ui.view.peserta.DestinasiDetailPeserta
import com.example.a13_179.ui.view.peserta.DestinasiDetailPeserta.id_peserta
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch


class DetailPesertaViewModel(
    private val pesertaRepository: PesertaRepository
) : ViewModel() {

    var uiState by mutableStateOf(DetailPesertaUiState())
        private set
    fun getDetailPeserta(id_peserta:Int) {
        viewModelScope.launch {
            uiState = DetailPesertaUiState(isLoading = true)
            try {
                val peserta = pesertaRepository.getPesertaById(id_peserta)

                // Mengubah state dengan data bangunan yang diterima
                uiState = DetailPesertaUiState(detailPesertaUiEvent = peserta.toDetailPesertaUiEvent())
            } catch (e: Exception) {
                e.printStackTrace()

                // Menangani error jika request gagal
                uiState = DetailPesertaUiState(isError = true, errorMessage = "Failed to fetch details: ${e.message}")
            }
        }
    }
}

data class DetailPesertaUiState(
    val detailPesertaUiEvent: InsertPesertaUiEvent = InsertPesertaUiEvent(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = "",
) {
    val isUiEventNotEmpty: Boolean
        get() = detailPesertaUiEvent != InsertPesertaUiEvent()
}
//memindahkan data dari entity ke ui
fun Peserta.toDetailPesertaUiEvent(): InsertPesertaUiEvent {
    return InsertPesertaUiEvent(
        id_peserta = id_peserta,
        nama_peserta = nama_peserta,
        email = email,
        nomor_telepon = nomor_telepon)
}