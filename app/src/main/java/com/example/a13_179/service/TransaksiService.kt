package com.example.a13_179.service


import com.example.a13_179.model.AllTransaksiResponse
import com.example.a13_179.model.DetailTransaksiResponse
import com.example.a13_179.model.Transaksi
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface TransaksiService {
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json",
    )
    @GET("transaksi")
    suspend fun getAllTransaksi(): AllTransaksiResponse

    @POST("transaksi/store")
    suspend fun insertTransaksi(@Body transaksi: Transaksi)

    @GET("transaksi/{id_transaksi}")
    suspend fun getTransaksiById(@Path("id_transaksi") id_transaksi: Int): DetailTransaksiResponse

    @DELETE("transaksi/{id_transaksi}")
    suspend fun deleteTransaksi(@Path("id_transaksi") id_transaksi: Int): Response<Void>
}
