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

sealed class HomeUiStatePeserta {
    data class Success(val peserta: List<Peserta>) : HomeUiStatePeserta()
    object  Error : HomeUiStatePeserta()
    object Loading : HomeUiStatePeserta()
}

class HomePesertaViewModel (private val psrta: PesertaRepository) : ViewModel(){
    var psrtaUIState: HomeUiStatePeserta by mutableStateOf(HomeUiStatePeserta.Loading)
        private set
    init {
        getPsrta()
    }

    fun getPsrta(){
        viewModelScope.launch {
            psrtaUIState = HomeUiStatePeserta.Loading
            psrtaUIState = try {
                HomeUiStatePeserta.Success(psrta.getPeserta())
            }catch (e:IOException){
                HomeUiStatePeserta.Error
            }catch (e:HttpException){
                HomeUiStatePeserta.Error
            }
        }
    }

    fun deletePsrta(id_peserta: Int){
        viewModelScope.launch {
            try {
                psrta.deletePeserta(id_peserta)
            }catch (e:IOException){
                HomeUiStatePeserta.Error
            }catch (e:HttpException){
                HomeUiStatePeserta.Error
            }
        }
    }
}