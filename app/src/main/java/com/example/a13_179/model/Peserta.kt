package com.example.a13_179.model

import kotlinx.serialization.Serializable

@Serializable
data class AllPesertaResponse(
    val status: Boolean,
    val message: String,
    val data: List<Peserta>
)

@Serializable
data class DetailPesertaResponse(
    val status: Boolean,
    val message: String,
    val data: Peserta
)

@Serializable
data class Peserta(
    val id_peserta: Int,
    val nama_peserta: String,
    val email: String,
    val nomor_telepon: String
)
