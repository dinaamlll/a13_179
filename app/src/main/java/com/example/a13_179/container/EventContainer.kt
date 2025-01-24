package com.example.a13_179.container

import com.example.a13_179.repository.EventRepository
import com.example.a13_179.repository.NetworkEventRepository
import com.example.a13_179.repository.NetworkPesertaRepository
import com.example.a13_179.repository.NetworkTiketRepository
import com.example.a13_179.repository.NetworkTransaksiRepository
import com.example.a13_179.repository.PesertaRepository
import com.example.a13_179.repository.TiketRepository
import com.example.a13_179.repository.TransaksiRepository
import com.example.a13_179.service.EventService
import com.example.a13_179.service.PesertaService
import com.example.a13_179.service.TiketService
import com.example.a13_179.service.TransaksiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppContainer {
    val eventRepository: EventRepository
    val pesertaRepository: PesertaRepository
    val tiketRepository: TiketRepository
    val transaksiRepository: TransaksiRepository
}

class MainContainer : AppContainer {

    private val baseUrl = "http://10.0.2.2:3000/api/" // localhost diganti IP jika dijalankan di perangkat fisik
    private val json = Json { ignoreUnknownKeys = true }
    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl).build()

    // Event Service dan Repository
    private val eventService: EventService by lazy {
        retrofit.create(EventService::class.java)
    }
    override val eventRepository: EventRepository by lazy {
        NetworkEventRepository(eventService)
    }

    // Peserta Service dan Repository
    private val pesertaService: PesertaService by lazy {
        retrofit.create(PesertaService::class.java)
    }
    override val pesertaRepository: PesertaRepository by lazy {
        NetworkPesertaRepository(pesertaService)
    }

    // Tiket Service dan Repository
    private val tiketService: TiketService by lazy {
        retrofit.create(TiketService::class.java)
    }
    override val tiketRepository: TiketRepository by lazy {
        NetworkTiketRepository(tiketService)
    }

    // Transaksi Service dan Repository
    private val transaksiService: TransaksiService by lazy {
        retrofit.create(TransaksiService::class.java)
    }
    override val transaksiRepository: TransaksiRepository by lazy {
        NetworkTransaksiRepository(transaksiService)
    }
}
