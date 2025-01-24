package com.example.a13_179.repository

import com.example.a13_179.model.Transaksi
import com.example.a13_179.service.TransaksiService
import java.io.IOException

interface TransaksiRepository {
    suspend fun getTransaksi(): List<Transaksi>
    suspend fun insertTransaksi(transaksi: Transaksi)
    suspend fun deleteTransaksi(id_transaksi: Int)
    suspend fun getTransaksiById(id_transaksi: Int): Transaksi
}

class NetworkTransaksiRepository(
    private val transaksiApiService: TransaksiService
) : TransaksiRepository {
    override suspend fun getTransaksi(): List<Transaksi> =
        transaksiApiService.getAllTransaksi().data

    override suspend fun insertTransaksi(transaksi: Transaksi) {
        transaksiApiService.insertTransaksi(transaksi)
    }

    override suspend fun deleteTransaksi(id_transaksi: Int) {
        try {
            val response = transaksiApiService.deleteTransaksi(id_transaksi)
            if (!response.isSuccessful) {
                throw IOException("Failed to delete transaksi. HTTP Status Code: " +
                        "${response.code()}")
            } else {
                response.message()
                println(response.message())
            }
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getTransaksiById(id_transaksi: Int): Transaksi {
        return transaksiApiService.getTransaksiById(id_transaksi).data
    }
}
