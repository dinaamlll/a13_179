package com.example.a13_179.service

import com.example.a13_179.model.Event
import com.example.a13_179.model.DetailEventResponse
import com.example.a13_179.model.EventResponse
import retrofit2.Response
import retrofit2.http.*

interface EventService {
    @Headers("Accept: application/json", "Content-Type: application/json")
    @GET("event")
    suspend fun getAllEvent(): EventResponse

    @POST("event/store")
    suspend fun insertEvent(@Body event: Event): Response<Unit>

    @GET("event/{id_event}")
    suspend fun getEventById(@Path("id_event") id_event: Int): DetailEventResponse

    @PUT("event/{id_event}")
    suspend fun updateEvent(@Path("id_event") id_event: Int, @Body event: Event): Response<Unit>

    @DELETE("event/{id_event}")
    suspend fun deleteEvent(@Path("id_event") id_event: Int): Response<Unit>
}
