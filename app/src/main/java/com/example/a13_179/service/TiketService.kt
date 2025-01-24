package com.example.a13_179.service


import com.example.a13_179.model.AllTiketResponse
import com.example.a13_179.model.DetailTiketResponse
import com.example.a13_179.model.Tiket
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface TiketService {
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json",
    )
    @GET("tiket")
    suspend fun getAllTiket(): AllTiketResponse

    @POST("ttiket/store")
    suspend fun insertTiket(@Body ticket: Tiket)

    @GET("tiket/{id_tiket}")
    suspend fun getTiketById(@Path("id_tiket") id_tiket: Int): DetailTiketResponse

    @PUT("tiket/{id_tiket}")
    suspend fun updateTiket(@Path("id_tiket") id_tiket: Int, @Body ticket: Tiket)

    @DELETE("tiket/{id_tiket}")
    suspend fun deleteTiket(@Path("id_tiket") id_tiket: Int): Response<Void>
}
