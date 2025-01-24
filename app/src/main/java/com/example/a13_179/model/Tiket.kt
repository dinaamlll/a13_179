package com.example.a13_179.model

import kotlinx.serialization.Serializable

@Serializable
data class AllTiketResponse(
    val status: Boolean,
    val message: String,
    val data: List<Tiket>
)

@Serializable
data class DetailTiketResponse(
    val status: Boolean,
    val message: String,
    val data: Tiket
)

@Serializable
data class Tiket(
    val id_tiket: Int,
    val id_event: Int,
    val id_pengguna: Int,
    val kapasitas_tiket: String,
    val harga_tiket: String
)
