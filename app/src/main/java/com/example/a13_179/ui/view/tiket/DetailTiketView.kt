package com.example.a13_179.ui.view.tiket

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
import com.example.a13_179.ui.customwidget.BottomAppBarDefaults
import com.example.a13_179.ui.customwidget.CostumeTopAppBar
import com.example.a13_179.ui.navigation.DestinasiNavigasi
import com.example.a13_179.ui.view.peserta.DestinasiDetailPeserta
import com.example.a13_179.ui.view.tiket.DestinasiDetailTiket.id_tiket
import com.example.a13_179.ui.viewmodel.tiket.DetailTiketUiState
import com.example.a13_179.ui.viewmodel.tiket.DetailTiketViewModel
import com.example.a13_179.ui.viewmodel.tiket.PenyediaViewModelTiket



object DestinasiDetailTiket : DestinasiNavigasi {
    override val route = "detail_tiket" // base route
    const val id_tiket = "id_tiket" // Nama parameter untuk id
    val routesWithArg = "$route/{$id_tiket}" // Route yang menerima id sebagai argumen
    override val titleRes = "Detail Tiket" // Title untuk halaman ini
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailTiketView(
    id_tiket: Int,
    onEventClick: () -> Unit,
    onPesertaClick: () -> Unit,
    onTiketClick: () -> Unit,
    onTransaksiClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DetailTiketViewModel = viewModel(factory = PenyediaViewModelTiket.Factory),
    onEditClick: (Int) -> Unit = {},
    onBackClick: () -> Unit,
) {
    val tiket = viewModel.uiState.detailTiketUiEvent

// Menggunakan id_tiket sebagai Int, pastikan bahwa id_tiket sudah dalam bentuk Int.
    LaunchedEffect(id_tiket) {
        viewModel.getDetailTiket(id_tiket) // id_tiket diteruskan sebagai Int
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
                onClick = { onEditClick(tiket.id_tiket) },
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(imageVector = Icons.Filled.Edit, contentDescription = "Edit Tiket")
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
                                    text = "Id Tiket: ${tiket.id_tiket}",
                                    style = MaterialTheme.typography.bodyLarge)
                                Text(
                                    text = "Nama Peserta: ${tiket.id_event}"
                                    , style = MaterialTheme.typography.bodyLarge)
                                Text(text = "Email: ${tiket.id_peserta}"
                                    , style = MaterialTheme.typography.bodyLarge)
                                Text(text = "Nomor Telepon: ${tiket.kapasitas_tiket}"
                                    , style = MaterialTheme.typography.bodyLarge)
                                Text(text = "Nomor Telepon: ${tiket.harga_tiket}"
                                    , style = MaterialTheme.typography.bodyLarge)
                            }
                        }
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            Button(onClick = { onEditClick(tiket.id_peserta) }) {
                                Text("Edit Data")
                            }

                    }
                    }
                }
            }
        }
    )
}