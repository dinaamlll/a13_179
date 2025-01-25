package com.example.a13_179.ui.viewmodel.tiket

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.a13_179.model.Tiket
import com.example.a13_179.repository.TiketRepository
import kotlinx.coroutines.launch

class InsertTiketViewModel(private val tkt: TiketRepository) : ViewModel() {
    var TiketuiState by mutableStateOf(InsertTiketUiState())

    fun updateInsertTktState(insertTiketUiEvent: InsertTiketUiEvent) {
        TiketuiState = InsertTiketUiState(insertTiketUiEvent =insertTiketUiEvent)
    }

    suspend fun insertTkt() {
        viewModelScope.launch {
            try {
                tkt.insertTiket(TiketuiState.insertTiketUiEvent.toTkt())
            }catch (e:Exception){
                e.printStackTrace()
            }
        }
    }
}

data class InsertTiketUiState(
    val insertTiketUiEvent: InsertTiketUiEvent = InsertTiketUiEvent()
)

data class InsertTiketUiEvent(
    val id_tiket: Int? = null,  //  Int? untuk memungkinkan nilai null
    val kapasitas_tiket:String = "",
    val harga_tiket:String = "",
    val nomor_telepon:String = "",
)
fun InsertTiketUiEvent.toTkt(): Tiket = Tiket(
    id_tiket = id_tiket ?: 0,
    kapasitas_tiket = kapasitas_tiket,
    harga_tiket = harga_tiket
)

fun Tiket.toInsertTiketUiEvent():InsertTiketUiEvent = InsertTiketUiEvent(
    id_tiket= id_tiket,
    kapasitas_tiket = kapasitas_tiket,
    harga_tiket = harga_tiket,
)