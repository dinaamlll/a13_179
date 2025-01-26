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
    data class Success(val transaksi: List<Transaksi>) : HomeTransaksiUiState()
    object  Error : HomeTransaksiUiState()
    object Loading : HomeTransaksiUiState()
}

class HomeTransaksiViewModel (private val trnsksi: TransaksiRepository) : ViewModel() {
    var trnsksiUIState: HomeTransaksiUiState by mutableStateOf(HomeTransaksiUiState.Loading)
        private set

    init {
        getTrnsksi()
    }

    fun getTrnsksi() {
        viewModelScope.launch {
            trnsksiUIState = HomeTransaksiUiState.Loading
            trnsksiUIState = try {
                HomeTransaksiUiState.Success(trnsksi.getTransaksi())
            } catch (e: IOException) {
                HomeTransaksiUiState.Error
            } catch (e: HttpException) {
                HomeTransaksiUiState.Error
            }
        }
    }


    fun deleteTrnsksi(id_transaksi: Int) {
        viewModelScope.launch {
            try {
                trnsksi.deleteTransaksi(id_transaksi)
            } catch (e: IOException) {
                HomeTransaksiUiState.Error
            } catch (e: HttpException) {
                HomeTransaksiUiState.Error
            }
        }
    }
}
