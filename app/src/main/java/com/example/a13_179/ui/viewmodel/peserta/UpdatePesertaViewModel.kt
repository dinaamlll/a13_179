package com.example.a13_179.ui.viewmodel.peserta

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.a13_179.model.Peserta
import com.example.a13_179.repository.PesertaRepository
import com.example.a13_179.ui.view.peserta.DestinasiUpdatePeserta
import kotlinx.coroutines.launch

class UpdatePesertaViewModel(
    savedStateHandle: SavedStateHandle,
    private val psrta: PesertaRepository
) : ViewModel() {

    // Retrieve the NIM from SavedStateHandle
    val id_peserta: Int = checkNotNull(savedStateHandle[DestinasiUpdatePeserta.ID_PESERTA])

    var PesertauiState = mutableStateOf(InsertPesertaUiState())
        private set

    init {
        ambilPeserta()
    }

    private fun ambilPeserta() {
        viewModelScope.launch {
            try {
                val peserta = psrta.getPesertaById(id_peserta)
                peserta?.let {
                    PesertauiState.value = it.toInsertPesertaUIEvent() // Update state with the fetched data
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    // Update the mahasiswa information
    fun updatePsrta(id_peserta: Int, peserta: Peserta) {
        viewModelScope.launch {
            try {
                psrta.updatePeserta(id_peserta, peserta)


            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    // Update the UI state with a new InsertUiEvent
    fun updatePesertaState(insertPesertaUiEvent: InsertPesertaUiEvent) {
        PesertauiState.value = PesertauiState.value.copy(insertPesertaUiEvent = insertPesertaUiEvent)
    }
}

fun Peserta.toInsertPesertaUIEvent(): InsertPesertaUiState = InsertPesertaUiState(
    insertPesertaUiEvent = this.toDetailPesertaUiEvent()
)