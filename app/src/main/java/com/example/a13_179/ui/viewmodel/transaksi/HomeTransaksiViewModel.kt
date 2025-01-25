package com.example.a13_179.ui.viewmodel.transaksi

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.example.a13_179.model.Transaksi
import com.example.a13_179.repository.TransaksiRepository
import kotlinx.coroutines.launch
import okio.IOException

sealed class HomeTransaksiUiState {
    data class Success(val transaksi: List<Transaksi>) : HomeTransaksUiState()
    object  Error : HomeTransaksUiState()
    object Loading : HomeTransaksUiState()
}

class HomeTransaksViewModel (private val trnsksi: TransaksiRepository) : ViewModel() {
    var trnsksiUIState: HomeTransaksUiState by mutableStateOf(HomeTransaksUiState.Loading)
        private set

    init {
        getTrnsksi()
    }

    fun getTrnsksi() {
        viewModelScope.launch {
            trnsksiUIState = HomeTransaksUiState.Loading
            trnsksiUIState = try {
                HomeTransaksUiState.Success(trnsksi.getTransaksi())
            } catch (e: IOException) {
                HomeTransaksUiState.Error
            } catch (e: HttpException) {
                HomeTransaksUiState.Error
            }
        }
    }
}