package com.example.a13_179.ui.viewmodel.peserta


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.example.a13_179.model.Peserta
import com.example.a13_179.repository.PesertaRepository
import kotlinx.coroutines.launch
import okio.IOException

sealed class HomePesertaUiState {
    data class Success(val peserta: List<Peserta>) : HomePesertaUiState()
    object  Error : HomePesertaUiState()
    object Loading : HomePesertaUiState()
}

class HomePesertaViewModel (private val psrta: PesertaRepository) : ViewModel(){
    var psrtaUIState: HomePesertaUiState by mutableStateOf(HomePesertaUiState.Loading)
        private set
    init {
        getPsrta()
    }

    fun getPsrta(){
        viewModelScope.launch {
            psrtaUIState = HomePesertaUiState.Loading
            psrtaUIState = try {
                HomePesertaUiState.Success(psrta.getPeserta())
            }catch (e:IOException){
                HomePesertaUiState.Error
            }catch (e:HttpException){
                HomePesertaUiState.Error
            }
        }
    }

    fun deletePsrta(id_peserta: Int){
        viewModelScope.launch {
            try {
                psrta.deletePeserta(id_peserta)
            }catch (e:IOException){
                HomePesertaUiState.Error
            }catch (e:HttpException){
                HomePesertaUiState.Error
            }
        }
    }
}