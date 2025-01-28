package com.example.a13_179.ui.view.peserta

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.a13_179.R
import com.example.a13_179.model.Peserta
import com.example.a13_179.ui.customwidget.BottomAppBarDefaults
import com.example.a13_179.ui.customwidget.CostumeTopAppBar
import com.example.a13_179.ui.navigation.DestinasiNavigasi
import com.example.a13_179.ui.view.tiket.DestinasiUpdateTiket
import com.example.a13_179.ui.view.tiket.OnError
import com.example.a13_179.ui.view.tiket.OnLoading
import com.example.a13_179.ui.viewmodel.event.PenyediaViewModelEvent
import com.example.a13_179.ui.viewmodel.peserta.HomePesertaUiState
import com.example.a13_179.ui.viewmodel.peserta.HomePesertaViewModel
import com.example.a13_179.ui.viewmodel.peserta.PenyediaViewModelPeserta

object DestinasiHomePeserta : DestinasiNavigasi {
    override val route = "home_peserta"
    override val titleRes = "Home Peserta"
    const val id_transaksi = "id_transaksi"
    val routesWithArg = "$route/{$id_transaksi}"
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomePesertaScreen(
    navigateToItemEntry: () -> Unit,
    modifier: Modifier = Modifier,
    onDetailClick: (Int) -> Unit = {},
    onEventClick: () -> Unit,
    onPesertaClick: () -> Unit,
    onTiketClick: () -> Unit,
    onTransaksiClick: () -> Unit,
    viewModel: HomePesertaViewModel = viewModel(factory = PenyediaViewModelPeserta.Factory)
) {

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()


    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiHomePeserta.titleRes,
                canNavigateBack = false,
                scrollBehavior = scrollBehavior,
                modifier = modifier,
                onRefresh = {
                    viewModel.getPsrta() }

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
                onClick = navigateToItemEntry,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(18.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Peserta"
                )
            }
        }
    ) { innerPadding ->
        HomeStatus(
            homePesertaUiState = viewModel.psrtaUIState,
            retryAction = { viewModel.getPsrta() },
            modifier = Modifier.padding(innerPadding),
            onDetailClick = onDetailClick,
            onDeleteClick = {
                viewModel.deletePsrta(it.id_peserta)
                viewModel.getPsrta()
            }
        )
    }
}

@Composable
fun HomeStatus(
    onDeleteClick: (Peserta) -> Unit,
    onDetailClick: (Int) -> Unit,
    homePesertaUiState: HomePesertaUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier

) {

    when (homePesertaUiState) {
        is HomePesertaUiState.Loading -> OnLoading(modifier = modifier.fillMaxSize())
        is HomePesertaUiState.Success ->
            if (homePesertaUiState.peserta.isEmpty()) {
                Box(
                    modifier = modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "Tidak Ada Data Peserta")
                }
            } else {
                PesertaLayout(
                    peserta = homePesertaUiState.peserta,
                    modifier = modifier.fillMaxWidth(),
                    onDetailClick = {
                        onDetailClick(it.id_peserta)
                    },
                    onDeleteClick = {
                        onDeleteClick(it)
                    }
                )
            }
        is HomePesertaUiState.Error -> OnError(retryAction, modifier = modifier.fillMaxSize())
    }
}

@Composable
fun PesertaLayout(
    peserta: List<Peserta>,
    modifier: Modifier = Modifier,
    onDetailClick: (Peserta) -> Unit,
    onDeleteClick: (Peserta) -> Unit = {}
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(peserta) { peserta ->
            PesertaCard(
                peserta = peserta,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onDetailClick(peserta) },
                onDeleteClick = {
                    onDeleteClick(peserta)
                }
            )
        }
    }
}

@Composable
fun PesertaCard(
    peserta: Peserta,
    modifier: Modifier = Modifier,
    onDeleteClick: (Peserta) -> Unit = {}
) {
    Card(
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = peserta.id_peserta.toString(),
                    style = MaterialTheme.typography.titleLarge
                )
                Spacer(Modifier.weight(1f))
                IconButton(onClick = { onDeleteClick(peserta) }) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = null
                    )
                }
                Text(
                    text = peserta.nama_peserta,
                    style = MaterialTheme.typography.titleMedium
                )
            }
            Text(
                text = peserta.email,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = peserta.nomor_telepon,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}