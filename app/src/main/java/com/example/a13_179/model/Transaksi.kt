package com.example.a13_179.model

import kotlinx.serialization.Serializable

@Serializable
data class AllTransaksiResponse(
    val status: Boolean,
    val message: String,
    val data: List<Transaksi>
)

@Serializable
data class DetailTransaksiResponse(
    val status: Boolean,
    val message: String,
    val data: Transaksi
)

@Serializable
data class Transaksi(
    val id_transaksi: Int,
    val id_tiket: Int,
    val jumlah_tiket: Int,
    val jumlah_pembayaran: Int,
    val tanggal_transaksi: String
)
