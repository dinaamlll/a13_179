package com.example.a13_179.ui.viewmodel.tiket

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.example.a13_179.model.Tiket
import com.example.a13_179.repository.TiketRepository
import kotlinx.coroutines.launch
import okio.IOException

sealed class HomeTiketUiState {
    data class Success(val tiket: List<Tiket>) : HomeTiketUiState()
    object  Error : HomeTiketUiState()
    object Loading : HomeTiketUiState()
}

class HomeTiketViewModel (private val tkt: TiketRepository) : ViewModel(){
    var tktUIState: HomeTiketUiState by mutableStateOf(HomeTiketUiState.Loading)
        private set
    init {
        getTkt()
    }

    fun getTkt(){
        viewModelScope.launch {
            tktUIState = HomeTiketUiState.Loading
            tktUIState = try {
                HomeTiketUiState.Success(tkt.getTiket())
            }catch (e:IOException){
                HomeTiketUiState.Error
            }catch (e:HttpException){
                HomeTiketUiState.Error
            }
        }
    }

    fun deleteTkt(id_tiket: Int){
        viewModelScope.launch {
            try {
                tkt.deleteTiket(id_tiket)
            }catch (e:IOException){
                HomeTiketUiState.Error
            }catch (e:HttpException){
                HomeTiketUiState.Error
            }
        }
    }
}