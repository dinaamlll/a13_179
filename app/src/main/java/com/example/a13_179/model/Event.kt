package com.example.a13_179.model


import kotlinx.serialization.Serializable
@Serializable
data class EventResponse(
    val status: Boolean,
    val message: String,
    val data: List<Event>
)

@Serializable
data class DetailEventResponse(
    val status: Boolean,
    val message: String,
    val data: Event
)
@Serializable
data class Event (
    val id_event: Int,
    val nama_event: String,
    val deskripsi_event: String,
    val tanggal_event: String,
    val lokasi_event: String
)
