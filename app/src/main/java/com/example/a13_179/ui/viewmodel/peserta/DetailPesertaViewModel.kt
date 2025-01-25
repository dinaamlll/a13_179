package com.example.a13_179.ui.viewmodel.peserta

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.a13_179.model.Event
import com.example.a13_179.model.Peserta
import com.example.a13_179.repository.PesertaRepository
import com.example.a13_179.ui.view.peserta.DestinasiDetailPeserta
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

sealed class DetailPesertaUiState {
    data class Success(val peserta: Peserta) : DetailPesertaUiState()
    object Error : DetailPesertaUiState()
    object Loading : DetailPesertaUiState()
}

class DetailPesertaViewModel(
    savedStateHandle: SavedStateHandle,
    private val psrta: PesertaRepository
) : ViewModel() {

    private val _idPeserta: Int = savedStateHandle.get<String>(DestinasiDetailPeserta.ID_PESERTA)
        ?.toIntOrNull()
        ?: throw IllegalArgumentException("Invalid ID_PESERTA value")
    // StateFlow untuk menyimpan status UI
    private val _detailPesertaUiState = MutableStateFlow<DetailPesertaUiState>(DetailPesertaUiState.Loading)
    val detailPesertaUiState: StateFlow<DetailPesertaUiState> = _detailPesertaUiState

    init {
        getDetailPeserta()
    }

    fun getDetailPeserta() {
        viewModelScope.launch {
            try {
                // Set loading state
                _detailPesertaUiState.value = DetailPesertaUiState.Loading

                // Fetch mahasiswa data dari repository
                val peserta = psrta.getPesertaById(_idPeserta)

                if (peserta != null) {
                    // Jika data ditemukan, emit sukses
                    _detailPesertaUiState.value = DetailPesertaUiState.Success(peserta)
                } else {
                    // Jika data tidak ditemukan, emit error
                    _detailPesertaUiState.value = DetailPesertaUiState.Error
                }
            } catch (e: Exception) {
                // Emit error jika terjadi exception
                _detailPesertaUiState.value = DetailPesertaUiState.Error
            }
        }
    }
}

//memindahkan data dari entity ke ui
fun Event.toDetailPesertaUiEvent(): InsertPesertaUiEvent {
    return InsertPesertaUiEvent(
        id_peserta = id_peserta,
        nama_peserta = nama_peserta,
        email = email,
        nomor_telepon = nomor_telepon)
}