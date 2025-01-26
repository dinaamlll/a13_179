package com.example.a13_179.ui.viewmodel.transaksi

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.a13_179.model.Tiket
import com.example.a13_179.model.Transaksi
import com.example.a13_179.repository.TiketRepository
import com.example.a13_179.repository.TransaksiRepository
import com.example.a13_179.ui.view.tiket.DestinasiDetailTiket
import com.example.a13_179.ui.view.transaksi.DestinasiDetailTransaksi
import com.example.a13_179.ui.viewmodel.tiket.DetailTiketUiState
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

sealed class DetailTransaksiUiState {
    data class Success(val transaski: Transaksi) : DetailTransaksiUiState()
    object Error : DetailTransaksiUiState()
    object Loading : DetailTransaksiUiState()
}

class DetailTransaksiViewModel(
    savedStateHandle: SavedStateHandle,
    private val trnsksi: TransaksiRepository
) : ViewModel() {

    private val _idTransaksi: Int = savedStateHandle.get<String>(DestinasiDetailTransaksi.ID_TRANSAKSI)
        ?.toIntOrNull()
        ?: throw IllegalArgumentException("Invalid ID_PESERTA value")
    // StateFlow untuk menyimpan status UI
    private val _detailTransaksiUiState = MutableStateFlow<DetailTransaksiUiState>(DetailTransaksiUiState.Loading)
    val detailTransaksiUiState: StateFlow<DetailTransaksiUiState> = _detailTransaksiUiState

    init {
        getDetailTransaksi()
    }

    fun getDetailTransaksi() {
        viewModelScope.launch {
            try {
                // Set loading state
                _detailTransaksiUiState.value = DetailTransaksiUiState.Loading

                // Fetch mahasiswa data dari repository
                val transaksi = trnsksi.getTransaksiById(_idTransaksi)

                if (transaksi != null) {
                    // Jika data ditemukan, emit sukses
                    _detailTransaksiUiState.value = DetailTransaksiUiState.Success(transaksi)
                } else {
                    // Jika data tidak ditemukan, emit error
                    _detailTransaksiUiState.value = DetailTransaksiUiState.Error
                }
            } catch (e: Exception) {
                // Emit error jika terjadi exception
                _detailTransaksiUiState.value = DetailTransaksiUiState.Error
            }
        }
    }
}

//memindahkan data dari entity ke ui
fun Transaksi.toDetailTransaksiUiEvent(): InsertTransaksiUiEvent {
    return InsertTransaksiUiEvent(
        id_transaksi = id_transaksi,
       id_tiket = id_tiket,
        jumlah_tiket = jumlah_tiket,
        jumlah_pembayaran = jumlah_pembayaran,
        tanggal_transaksi = tanggal_transaksi
    )
}