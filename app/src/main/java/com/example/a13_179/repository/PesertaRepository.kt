package com.example.a13_179.repository

import com.example.a13_179.model.Peserta
import com.example.a13_179.service.PesertaService
import java.io.IOException

interface PesertaRepository {
    suspend fun getPeserta(): List<Peserta>
    suspend fun insertPeserta(peserta: Peserta)
    suspend fun updatePeserta(id_peserta: Int, peserta: Peserta)
    suspend fun deletePeserta(id_peserta: Int)
    suspend fun getPesertaById(id_peserta: Int): Peserta
}

class NetworkPesertaRepository(
    private val pesertaApiService: PesertaService
) : PesertaRepository {
    override suspend fun getPeserta(): List<Peserta> =
        pesertaApiService.getAllPeserta().data

    override suspend fun insertPeserta(peserta: Peserta) {
        pesertaApiService.insertPeserta(peserta)
    }

    override suspend fun updatePeserta(id_peserta: Int, peserta: Peserta) {
        pesertaApiService.updatePeserta(id_peserta, peserta)
    }

    override suspend fun deletePeserta(id_peserta: Int) {
        try {
            val response = pesertaApiService.deletePeserta(id_peserta)
            if (!response.isSuccessful) {
                throw IOException("Failed to delete peserta. HTTP Status Code: " +
                        "${response.code()}")
            } else {
                response.message()
                println(response.message())
            }
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getPesertaById(id_peserta: Int): Peserta {
        return pesertaApiService.getPesertaById(id_peserta).data
    }
}
