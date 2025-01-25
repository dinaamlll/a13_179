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

sealed class HomeUiStateTiket {
    data class Success(val tiket: List<Tiket>) : HomeUiStateTiket()
    object  Error : HomeUiStateTiket()
    object Loading : HomeUiStateTiket()
}

class HomeTiketViewModel (private val tkt: TiketRepository) : ViewModel(){
    var tktUIState: HomeUiStateTiket by mutableStateOf(HomeUiStateTiket.Loading)
        private set
    init {
        getTkt()
    }

    fun getTkt(){
        viewModelScope.launch {
            tktUIState = HomeUiStateTiket.Loading
            tktUIState = try {
                HomeUiStateTiket.Success(tkt.getTiket())
            }catch (e:IOException){
                HomeUiStateTiket.Error
            }catch (e:HttpException){
                HomeUiStateTiket.Error
            }
        }
    }

    fun deleteTkt(id_tiket: Int){
        viewModelScope.launch {
            try {
                tkt.deleteTiket(id_tiket)
            }catch (e:IOException){
                HomeUiStateTiket.Error
            }catch (e:HttpException){
                HomeUiStateTiket.Error
            }
        }
    }
}