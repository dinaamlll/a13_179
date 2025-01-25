package com.example.a13_179.ui.viewmodel.tiket

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.a13_179.model.Tiket
import com.example.a13_179.repository.TiketRepository
import com.example.a13_179.ui.view.tiket.DestinasiUpdateTiket
import kotlinx.coroutines.launch

class UpdateTiketViewModel(
    savedStateHandle: SavedStateHandle,
    private val tkt: TiketRepository
) : ViewModel() {


    val id_tiket: Int = checkNotNull(savedStateHandle[DestinasiUpdateTiket.ID_TIKET])

    var TiketuiState = mutableStateOf(InsertTiketUiState())
        private set

    init {
        ambilTiket()
    }

    private fun ambilTiket() {
        viewModelScope.launch {
            try {
                val tiket = tkt.getTiketById(id_tiket)
                tiket?.let {
                    TiketuiState.value = it.toInsertTiketUIEvent() // Update state with the fetched data
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun updateTkt(id_tiket: Int, tiket: Tiket) {
        viewModelScope.launch {
            try {
                tkt.updateTiket(id_tiket, tiket)


            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    // Update the UI state with a new InsertUiEvent
    fun updateTiketState(insertTiketUiEvent: InsertTiketUiEvent) {
        TiketuiState.value = TiketuiState.value.copy(insertTiketUiEvent = insertTiketUiEvent)
    }
}

fun Tiket.toInsertTiketUIEvent(): InsertTiketUiState = InsertTiketUiState(
    insertTiketUiEvent = this.toDetailTiketUiEvent()
)