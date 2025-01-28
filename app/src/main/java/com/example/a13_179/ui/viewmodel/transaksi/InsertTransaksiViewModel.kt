package com.example.a13_179.ui.viewmodel.transaksi

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.a13_179.model.Tiket
import com.example.a13_179.model.Transaksi
import com.example.a13_179.repository.TransaksiRepository
import kotlinx.coroutines.launch
import java.util.Date

class InsertTransaksiViewModel(private val trnsksi: TransaksiRepository) : ViewModel() {
    var TransaksiuiState by mutableStateOf(InsertTransaksiUiState())

    fun updateInsertTrnsksiState(insertTransaksiUiEvent: InsertTransaksiUiEvent) {
        TransaksiuiState = InsertTransaksiUiState(insertTransaksiUiEvent =insertTransaksiUiEvent)

    }

    suspend fun insertTrnsksi() {
        viewModelScope.launch {
            try {
                trnsksi.insertTransaksi(TransaksiuiState.insertTransaksiUiEvent.toTrnsksi())
            }catch (e:Exception){
                e.printStackTrace()
            }
        }
    }
}

data class InsertTransaksiUiState(
    val insertTransaksiUiEvent: InsertTransaksiUiEvent = InsertTransaksiUiEvent()
)

data class InsertTransaksiUiEvent(
    val id_transaksi: Int = 0 , //  Int? untuk memungkinkan nilai null
    val id_tiket: Int? = null,
    val jumlah_tiket: Int? = null,
    val jumlah_pembayaran: Int? = null,
    val tanggal_transaksi:String = "",
    val tiketList: List<Tiket> = emptyList()
)
fun InsertTransaksiUiEvent.toTrnsksi(): Transaksi = Transaksi(
    id_transaksi = id_transaksi ?: 0,
    id_tiket = id_tiket ?: 0,
    jumlah_tiket = jumlah_tiket ?: 0,
    jumlah_pembayaran = jumlah_pembayaran ?: 0,
    tanggal_transaksi = tanggal_transaksi
)

fun Transaksi.toInsertTransaksiUiEvent():InsertTransaksiUiEvent = InsertTransaksiUiEvent(
    id_transaksi= id_transaksi,
    id_tiket =id_tiket,
    jumlah_tiket = jumlah_tiket,
    jumlah_pembayaran = jumlah_pembayaran,
    tanggal_transaksi = tanggal_transaksi
)