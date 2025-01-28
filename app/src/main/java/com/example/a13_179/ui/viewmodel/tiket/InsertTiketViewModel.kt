package com.example.a13_179.ui.viewmodel.tiket

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.a13_179.model.Tiket
import com.example.a13_179.repository.TiketRepository
import kotlinx.coroutines.launch

class InsertTiketViewModel(private val tiket: TiketRepository) : ViewModel() {

    var uiState by mutableStateOf(InsertTiketUiState())
        private set

    fun updateInsertTktState(insertTiketUiEvent: InsertTiketUiEvent) {
        uiState = InsertTiketUiState(insertTiketUiEvent = insertTiketUiEvent)
    }

    fun insertTiket() {
        viewModelScope.launch {
            try {
                tiket.insertTiket(uiState.insertTiketUiEvent.toTkt())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}

// Data class untuk InsertTiketUiState
data class InsertTiketUiState(
    val insertTiketUiEvent: InsertTiketUiEvent = InsertTiketUiEvent()
)

// Data class untuk InsertTiketUiEvent
data class InsertTiketUiEvent(
    val id_tiket: Int = 0, // Ganti menjadi Int
    val id_event: Int = 0, // Ganti menjadi Int
    val id_peserta: Int = 0, // Ganti menjadi Int
    val kapasitas_tiket: Int = 0, // Nilai default untuk kapasitas_tiket
    val harga_tiket: Int = 0 // Nilai default untuk harga_tiket
)

// Mengubah InsertTiketUiEvent menjadi Tiket
fun InsertTiketUiEvent.toTkt(): Tiket = Tiket(
    id_tiket = id_tiket,
    id_event = id_event,
    id_peserta = id_peserta,
    kapasitas_tiket = kapasitas_tiket,
    harga_tiket = harga_tiket
)

// Mengubah Tiket menjadi InsertTiketUiState
fun Tiket.toTiketUiState(): InsertTiketUiState = InsertTiketUiState(
    insertTiketUiEvent = toInsertTiketUiEvent()
)

// Mengubah Tiket menjadi InsertTiketUiEvent
fun Tiket.toInsertTiketUiEvent(): InsertTiketUiEvent = InsertTiketUiEvent(
    id_tiket = id_tiket,
    id_event = id_event,
    id_peserta = id_peserta,
    kapasitas_tiket = kapasitas_tiket,
    harga_tiket = harga_tiket
)
