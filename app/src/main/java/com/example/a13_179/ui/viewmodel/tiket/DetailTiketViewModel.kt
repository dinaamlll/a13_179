package com.example.a13_179.ui.viewmodel.tiket

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.a13_179.model.Peserta
import com.example.a13_179.model.Tiket
import com.example.a13_179.repository.PesertaRepository
import com.example.a13_179.repository.TiketRepository
import com.example.a13_179.ui.view.peserta.DestinasiDetailPeserta
import com.example.a13_179.ui.view.tiket.DestinasiDetailTiket
import com.example.a13_179.ui.viewmodel.peserta.DetailPesertaUiState
import com.example.a13_179.ui.viewmodel.peserta.InsertPesertaUiEvent
import com.example.a13_179.ui.viewmodel.peserta.toDetailPesertaUiEvent
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class DetailTiketViewModel(
    savedStateHandle: SavedStateHandle,
    private val tiketRepository: TiketRepository
) : ViewModel() {

    var uiState by mutableStateOf(DetailTiketUiState())
        private set
    // StateFlow untuk menyimpan status UI

    fun getDetailTiket(id_tiket: Int) {
        viewModelScope.launch {
            uiState = DetailTiketUiState(isLoading = true)
            try {
                val tiket = tiketRepository.getTiketById(id_tiket)

                // Mengubah state dengan data tiket yang diterima
                uiState = DetailTiketUiState(detailTiketUiEvent = tiket.toDetailTiketUiEvent())
            } catch (e: Exception) {
                e.printStackTrace()

                // Menangani error jika request gagal
                uiState = DetailTiketUiState(isError = true, errorMessage = "Failed to fetch details: ${e.message}")
            }
        }
    }
}

data class DetailTiketUiState(
    val detailTiketUiEvent: InsertTiketUiEvent = InsertTiketUiEvent(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = "",
) {
    val isUiEventNotEmpty: Boolean
        get() = detailTiketUiEvent != InsertTiketUiEvent()
}

// memindahkan data dari entity ke UI
fun Tiket.toDetailTiketUiEvent(): InsertTiketUiEvent {
    return InsertTiketUiEvent(
        id_tiket = id_tiket,
        kapasitas_tiket = kapasitas_tiket, // Pastikan ini bertipe Int
        harga_tiket = harga_tiket // Pastikan ini bertipe Int
    )
}
