package com.example.a13_179.repository

import com.example.a13_179.model.Tiket
import com.example.a13_179.service.TiketService
import java.io.IOException

interface TiketRepository {
    suspend fun getTiket(): List<Tiket>
    suspend fun insertTiket(tiket: Tiket)
    suspend fun updateTiket(id_tiket: Int, tiket: Tiket)
    suspend fun deleteTiket(id_tiket: Int)
    suspend fun getTiketById(id_tiket: Int): Tiket
}

class NetworkTiketRepository(
    private val tiketApiService: TiketService
) : TiketRepository {
    override suspend fun getTiket(): List<Tiket> =
        tiketApiService.getAllTiket().data

    override suspend fun insertTiket(tiket: Tiket) {
        tiketApiService.insertTiket(tiket)
    }

    override suspend fun updateTiket(id_tiket: Int, tiket: Tiket) {
        tiketApiService.updateTiket(id_tiket, tiket)
    }

    override suspend fun deleteTiket(id_tiket: Int) {
        try {
            val response = tiketApiService.deleteTiket(id_tiket)
            if (!response.isSuccessful) {
                throw IOException("Failed to delete tiket. HTTP Status Code: " +
                        "${response.code()}")
            } else {
                response.message()
                println(response.message())
            }
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getTiketById(id_tiket: Int): Tiket {
        return tiketApiService.getTiketById(id_tiket).data
    }
}