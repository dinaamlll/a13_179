package com.example.a13_179.service

import com.example.a13_179.model.AllPesertaResponse
import com.example.a13_179.model.DetailPesertaResponse
import com.example.a13_179.model.Peserta
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface PesertaService {
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json",
    )
    @GET("peserta")
    suspend fun getAllPeserta(): AllPesertaResponse

    @POST("peserta/store")
    suspend fun insertPeserta(@Body participant: Peserta)

    @GET("peserta/{id_peserta}")
    suspend fun getPesertaById(@Path("id_peserta") id_peserta: Int): DetailPesertaResponse

    @PUT("peserta/{id_peserta}")
    suspend fun updatePeserta(@Path("id_peserta") id_peserta: Int, @Body peserta: Peserta)

    @DELETE("peserta/{id_peserta}")
    suspend fun deletePeserta(@Path("id_peserta") id_peserta: Int): Response<Void>
}
