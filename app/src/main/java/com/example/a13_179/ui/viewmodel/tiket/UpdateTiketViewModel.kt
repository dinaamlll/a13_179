package com.example.a13_179.ui.viewmodel.tiket

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.a13_179.model.Event
import com.example.a13_179.model.Peserta
import com.example.a13_179.model.Tiket
import com.example.a13_179.repository.EventRepository
import com.example.a13_179.repository.PesertaRepository
import com.example.a13_179.repository.TiketRepository
import com.example.a13_179.ui.view.peserta.DestinasiUpdatePeserta
import com.example.a13_179.ui.view.tiket.DestinasiUpdateTiket
import com.example.a13_179.ui.viewmodel.peserta.InsertPesertaUiEvent
import com.example.a13_179.ui.viewmodel.peserta.InsertPesertaUiState
import com.example.a13_179.ui.viewmodel.peserta.toPesertaUiState
import com.example.a13_179.ui.viewmodel.peserta.toPsrta
import kotlinx.coroutines.launch

class UpdateTiketViewModel(
    savedStateHandle: SavedStateHandle,
    private val repositoryTiket: TiketRepository,
) : ViewModel() {

    var updateTiketUiState by mutableStateOf(InsertTiketUiState())
        private set

    private val _id_tiket: String =
        checkNotNull(savedStateHandle[DestinasiUpdateTiket.id_tiket])

    init {
        // Pastikan _id_tiket adalah Int, misalnya jika _id_tiket sebelumnya bertipe String
        val idTiketInt = _id_tiket.toIntOrNull()

        // Periksa apakah _id_tiket berhasil diubah menjadi Int
        if (idTiketInt != null) {
            // Ambil data tiket berdasarkan ID ketika ViewModel diinisialisasi
            viewModelScope.launch {
                updateTiketUiState = repositoryTiket
                    .getTiketById(idTiketInt)  // Panggil dengan ID bertipe Int
                    .toTiketUiState()
            }
        } else {
            // Jika _id_tiket tidak valid (tidak bisa diubah menjadi Int), tangani kesalahan atau beri pesan
            println("ID Tiket tidak valid: $_id_tiket")
        }
    }

    // Fungsi untuk memperbarui UI state dengan event baru
    fun updateInsertTiketState(insertTiketUiEvent: InsertTiketUiEvent) {
        updateTiketUiState = InsertTiketUiState(insertTiketUiEvent = insertTiketUiEvent)
    }

    // Fungsi untuk memperbarui peserta di repository
    fun updateTiket() {
        viewModelScope.launch {
            try {
                // Pastikan _id_tiket bertipe Int
                val idTiketInt =
                    _id_tiket.toIntOrNull()  // Mengonversi String ke Int jika memungkinkan

                // Periksa jika id_tiket berhasil dikonversi menjadi Int
                if (idTiketInt != null) {
                    repositoryTiket.updateTiket(
                        idTiketInt,  // Kirim id_tiket yang sudah menjadi Int
                        updateTiketUiState.insertTiketUiEvent.toTkt()
                    )
                } else {
                    // Jika _id_tiket tidak bisa diubah menjadi Int, tampilkan error atau tangani dengan sesuai
                    println("ID Tiket tidak valid: $_id_tiket")
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}

