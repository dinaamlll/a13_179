package com.example.a13_179.ui.viewmodel.peserta

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.a13_179.model.Peserta
import com.example.a13_179.repository.PesertaRepository
import kotlinx.coroutines.launch

class InsertPesertaViewModel(
    private val peserta: PesertaRepository
): ViewModel() {
    var uiState by mutableStateOf(InsertPesertaUiState())
        private set

    fun updateInsertPesertaState(insertPesertaUiEvent: InsertPesertaUiEvent){
        uiState = InsertPesertaUiState(insertPesertaUiEvent = insertPesertaUiEvent)
    }

    fun insertPeserta() {
        viewModelScope.launch {
            try {
                peserta.insertPeserta(uiState.insertPesertaUiEvent.toPsrta())
            } catch (e:Exception){
                e.printStackTrace()
            }
        }
    }
}

data class InsertPesertaUiState(
    val insertPesertaUiEvent: InsertPesertaUiEvent = InsertPesertaUiEvent()
)
data class InsertPesertaUiEvent(
    val id_peserta: Int = 0,
    val nama_peserta: String = "",
    val email: String = "",
    val nomor_telepon: String = "",
)

fun InsertPesertaUiEvent.toPsrta(): Peserta = Peserta(
    id_peserta = id_peserta,
    nama_peserta = nama_peserta,
    email = email,
    nomor_telepon = nomor_telepon
)

fun Peserta.toPesertaUiState(): InsertPesertaUiState = InsertPesertaUiState(
    insertPesertaUiEvent = toInsertPesertaUiEvent()
)
fun Peserta.toInsertPesertaUiEvent(): InsertPesertaUiEvent = InsertPesertaUiEvent(
    id_peserta = id_peserta,
    nama_peserta = nama_peserta,
    email = email,
    nomor_telepon = nomor_telepon
)