package com.example.a13_179.ui.viewmodel.tiket

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.a13_179.model.Peserta
import com.example.a13_179.model.Tiket
import com.example.a13_179.repository.PesertaRepository
import com.example.a13_179.repository.TiketRepository
import com.example.a13_179.ui.view.peserta.DestinasiDetailPeserta
import com.example.a13_179.ui.view.tiket.DestinasiDetailTiket
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

sealed class DetailTiketUiState {
    data class Success(val tiket:Tiket) : DetailTiketUiState()
    object Error : DetailTiketUiState()
    object Loading : DetailTiketUiState()
}

class DetailTiketViewModel(
    savedStateHandle: SavedStateHandle,
    private val tkt: TiketRepository
) : ViewModel() {

    private val _idTiket: Int = savedStateHandle.get<String>(DestinasiDetailTiket.ID_TIKET)
        ?.toIntOrNull()
        ?: throw IllegalArgumentException("Invalid ID_PESERTA value")
    // StateFlow untuk menyimpan status UI
    private val _detailTiketUiState = MutableStateFlow<DetailTiketUiState>(DetailTiketUiState.Loading)
    val detailTiketUiState: StateFlow<DetailTiketUiState> = _detailTiketUiState

    init {
        getDetailTiket()
    }

    fun getDetailTiket() {
        viewModelScope.launch {
            try {
                // Set loading state
                _detailTiketUiState.value = DetailTiketUiState.Loading

                // Fetch mahasiswa data dari repository
                val tiket = tkt.getTiketById(_idTiket)

                if (tiket != null) {
                    // Jika data ditemukan, emit sukses
                    _detailTiketUiState.value = DetailTiketUiState.Success(tiket)
                } else {
                    // Jika data tidak ditemukan, emit error
                    _detailTiketUiState.value = DetailTiketUiState.Error
                }
            } catch (e: Exception) {
                // Emit error jika terjadi exception
                _detailTiketUiState.value = DetailTiketUiState.Error
            }
        }
    }
}

//memindahkan data dari entity ke ui
fun Tiket.toDetailTiketUiEvent(): InsertTiketUiEvent {
    return InsertTiketUiEvent(
        id_tiket = id_tiket,
        kapasitas_tiket = kapasitas_tiket,
        harga_tiket = harga_tiket
    )
}