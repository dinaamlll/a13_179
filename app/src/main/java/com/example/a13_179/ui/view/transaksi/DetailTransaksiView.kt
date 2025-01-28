package com.example.a13_179.ui.view.transaksi

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.a13_179.model.Tiket
import com.example.a13_179.model.Transaksi
import com.example.a13_179.ui.customwidget.BottomAppBarDefaults
import com.example.a13_179.ui.customwidget.CostumeTopAppBar
import com.example.a13_179.ui.navigation.DestinasiNavigasi
import com.example.a13_179.ui.view.peserta.DestinasiDetailPeserta
import com.example.a13_179.ui.viewmodel.tiket.DetailTiketUiState
import com.example.a13_179.ui.viewmodel.tiket.DetailTiketViewModel
import com.example.a13_179.ui.viewmodel.tiket.PenyediaViewModelTransaksi
import com.example.a13_179.ui.viewmodel.transaksi.DetailTransaksiUiState
import com.example.a13_179.ui.viewmodel.transaksi.DetailTransaksiViewModel


object DestinasiDetailTransaksi : DestinasiNavigasi {
    override val route = "detail_transaksi" // base route
    const val id_transaksi = "id_transaksi" // Nama parameter untuk id
    val routesWithArg = "$route/{$id_transaksi}" // Route yang menerima id sebagai argumen
    override val titleRes = "Detail Transaksi" // Title untuk halaman ini
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailTransaksiView(
    id_transaksi: Int,
    onBackClick: () -> Unit,
    onEventClick: () -> Unit,
    onPesertaClick: () -> Unit,
    onTiketClick: () -> Unit,
    onTransaksiClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DetailTransaksiViewModel = viewModel(factory = PenyediaViewModelTransaksi.Factory),
    onEditClick: (Int) -> Unit = {},
) {
    val transaksi = viewModel.uiState.detailTransaksiUiEvent

// Menggunakan id_tiket sebagai Int, pastikan bahwa id_tiket sudah dalam bentuk Int.
    LaunchedEffect(id_transaksi) {
        viewModel.getDetailTransaksi(id_transaksi) // id_tiket diteruskan sebagai Int
    }

    val isLoading = viewModel.uiState.isLoading
    val isError = viewModel.uiState.isError
    val errorMessage = viewModel.uiState.errorMessage
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(DestinasiDetailPeserta.titleRes)},
                navigationIcon = {
                    IconButton(onClick = {onBackClick() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors()
            )
        },
        bottomBar = {
            BottomAppBarDefaults(
                navController = rememberNavController(),
                onEventClick = onEventClick,
                onPesertaClick = onPesertaClick,
                onTiketClick = onTiketClick,
                onTransaksiClick = onTransaksiClick
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onEditClick(transaksi.id_transaksi) },
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(imageVector = Icons.Filled.Edit, contentDescription = "Edit Event")
            }
        },
        content = { paddingValues ->
            Box (
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                if (isLoading) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                } else if (isError) {
                    Text(
                        text = errorMessage,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.align(Alignment.Center)
                    )
                } else if (viewModel.uiState.isUiEventNotEmpty) {
                    Column (
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ){
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            elevation = CardDefaults.cardElevation(8.dp)
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp),
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Text(
                                    text = "Id Transaksi: ${transaksi.id_transaksi}",
                                    style = MaterialTheme.typography.bodyLarge)
                                Text(
                                    text = "Id Tiket: ${transaksi.id_tiket}"
                                    , style = MaterialTheme.typography.bodyLarge)
                                Text(text = "Jumlah Tiket: ${transaksi.jumlah_tiket}"
                                    , style = MaterialTheme.typography.bodyLarge)
                                Text(text = "Jumlah Pembayaran: ${transaksi.jumlah_pembayaran}"
                                    , style = MaterialTheme.typography.bodyLarge)
                                Text(text = "Tanggal Transaksi: ${transaksi.tanggal_transaksi}"
                                    , style = MaterialTheme.typography.bodyLarge)
                            }
                        }
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            Button(onClick = { onEditClick(transaksi.id_transaksi) }) {
                                Text("Edit Data")
                            }

                        }
                    }
                }
            }
        }
    )
}