package com.example.a13_179.ui.viewmodel.transaksi

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.a13_179.model.Peserta
import com.example.a13_179.model.Tiket
import com.example.a13_179.model.Transaksi
import com.example.a13_179.repository.PesertaRepository
import com.example.a13_179.repository.TiketRepository
import com.example.a13_179.repository.TransaksiRepository
import com.example.a13_179.ui.view.peserta.DestinasiDetailPeserta
import com.example.a13_179.ui.view.tiket.DestinasiDetailTiket
import com.example.a13_179.ui.view.transaksi.DestinasiDetailTransaksi
import com.example.a13_179.ui.viewmodel.event.DetailEventUiState
import com.example.a13_179.ui.viewmodel.event.InsertEventUiEvent
import com.example.a13_179.ui.viewmodel.event.toDetailEventUiEvent
import com.example.a13_179.ui.viewmodel.tiket.DetailTiketUiState
import com.example.a13_179.ui.viewmodel.tiket.InsertTiketUiEvent
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch


class DetailTransaksiViewModel(
    savedStateHandle: SavedStateHandle,
    private val transaksiRepository : TransaksiRepository
) : ViewModel() {

    var uiState by mutableStateOf(DetailTransaksiUiState())
        private set


    fun getDetailTransaksi(id_transaksi: Int) {
        viewModelScope.launch {
            uiState = DetailTransaksiUiState(isLoading = true)
            try {
                val transaksi = transaksiRepository.getTransaksiById(id_transaksi)

                // Mengubah state dengan data event yang diterima
                uiState = DetailTransaksiUiState(detailTransaksiUiEvent = transaksi.toDetailTransaksiUiEvent())
            } catch (e: Exception) {
                e.printStackTrace()

                // Menangani error jika request gagal
                uiState = DetailTransaksiUiState(
                    isError = true,
                    errorMessage = "Failed to fetch details: ${e.message}"
                )
            }
        }
    }
}
data class DetailTransaksiUiState(
    val detailTransaksiUiEvent: InsertTransaksiUiEvent = InsertTransaksiUiEvent(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = "",
) {
    val isUiEventNotEmpty: Boolean
        get() = detailTransaksiUiEvent != InsertTransaksiUiEvent()
}
//memindahkan data dari entity ke ui
fun Transaksi.toDetailTransaksiUiEvent(): InsertTransaksiUiEvent {
    return InsertTransaksiUiEvent(
        id_transaksi = id_transaksi,
        id_tiket = id_tiket,
        jumlah_tiket = jumlah_tiket,
        jumlah_pembayaran = jumlah_pembayaran
    )
}