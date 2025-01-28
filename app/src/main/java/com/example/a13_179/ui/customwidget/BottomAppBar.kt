package com.example.a13_179.ui.customwidget

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController

@Composable
fun BottomAppBarDefaults(
    navController: NavHostController,
    onEventClick: () -> Unit,
    onPesertaClick: () -> Unit,
    onTiketClick: () -> Unit,
    onTransaksiClick: () -> Unit
) {
    NavigationBar(
        containerColor = Color(0xFFFFC1E3), // Warna pink untuk latar belakang navigation bar
        contentColor = Color(0xFFAD1457)   // Warna pink gelap untuk ikon dan teks
    ) {
        // Item Navigasi untuk Event
        NavigationBarItem(
            icon = {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = "Event",
                    tint = Color(0xFFD81B60) // Warna pink medium untuk ikon Event
                )
            },
            label = { Text("Event", color = Color(0xFFAD1457)) }, // Warna pink gelap untuk label
            selected = false, // Logika pemilihan dapat diimplementasikan sesuai kebutuhan
            onClick = onEventClick
        )

        // Item Navigasi untuk Peserta
        NavigationBarItem(
            icon = {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Peserta",
                    tint = Color(0xFFD81B60) // Warna pink medium untuk ikon Peserta
                )
            },
            label = { Text("Peserta", color = Color(0xFFAD1457)) }, // Warna pink gelap untuk label
            selected = false,
            onClick = onPesertaClick
        )

        // Item Navigasi untuk Tiket
        NavigationBarItem(
            icon = {
                Icon(
                    imageVector = Icons.Default.DateRange,
                    contentDescription = "Tiket",
                    tint = Color(0xFFD81B60) // Warna pink medium untuk ikon Tiket
                )
            },
            label = { Text("Tiket", color = Color(0xFFAD1457)) }, // Warna pink gelap untuk label
            selected = false,
            onClick = onTiketClick
        )

        // Item Navigasi untuk Transaksi
        NavigationBarItem(
            icon = {
                Icon(
                    imageVector = Icons.Default.ShoppingCart,
                    contentDescription = "Transaksi",
                    tint = Color(0xFFD81B60) // Warna pink medium untuk ikon Transaksi
                )
            },
            label = { Text("Transaksi", color = Color(0xFFAD1457)) }, // Warna pink gelap untuk label
            selected = false,
            onClick = onTransaksiClick
        )
    }
}