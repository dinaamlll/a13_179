package com.example.a13_179.ui.viewmodel.peserta

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.a13_179.model.Peserta
import com.example.a13_179.repository.PesertaRepository
import kotlinx.coroutines.launch

class InsertPesertaViewModel(private val psrta: PesertaRepository) : ViewModel() {
    var PesertauiState by mutableStateOf(InsertPesertaUiState())

    fun updateInsertPsrtaState(insertPesertaUiEvent: InsertPesertaUiEvent) {
        PesertauiState = InsertPesertaUiState(insertPesertaUiEvent =insertPesertaUiEvent)
    }

    suspend fun insertMhs() {
        viewModelScope.launch {
            try {
                psrta.insertPeserta(PesertauiState.insertPesertaUiEvent.toPsrta())
            }catch (e:Exception){
                e.printStackTrace()
            }
        }
    }
}

data class InsertPesertaUiState(
    val insertPesertaUiEvent: InsertPesertaUiEvent = InsertPesertaUiEvent()
)

data class InsertPesertaUiEvent(
    val id_peserta: Int? = null,  //  Int? untuk memungkinkan nilai null
    val nama_peserta:String = "",
    val email:String = "",
    val nomor_telepon:String = "",
)
fun InsertPesertaUiEvent.toPsrta(): Peserta = Peserta(
    id_peserta = id_peserta ?: 0,
    nama_peserta = nama_peserta,
    email = email,
    nomor_telepon = nomor_telepon
)

fun Peserta.toInsertPesertaUiEvent():InsertPesertaUiEvent = InsertPesertaUiEvent(
    id_peserta= id_peserta,
    nama_peserta = nama_peserta,
    email = email,
    nomor_telepon = nomor_telepon
)