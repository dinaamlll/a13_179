package com.example.a13_179.ui.view.event

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.a13_179.R
import com.example.a13_179.model.Event
import com.example.a13_179.ui.customwidget.BottomAppBarDefaults
import com.example.a13_179.ui.customwidget.CostumeTopAppBar
import com.example.a13_179.ui.navigation.DestinasiNavigasi
import com.example.a13_179.ui.view.event.DestinasiDetailEvent.id_event
import com.example.a13_179.ui.view.peserta.HomeStatus
import com.example.a13_179.ui.view.tiket.OnLoading
import com.example.a13_179.ui.viewmodel.event.HomeEventUiState
import com.example.a13_179.ui.viewmodel.event.HomeEventViewModel
import com.example.a13_179.ui.viewmodel.event.PenyediaViewModelEvent

object DestinasiHomeEvent : DestinasiNavigasi {
    override val route = "home_event"
    override val titleRes = "Selamat Datang di Halaman Event"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeEventScreen(
    navigateToItemEntry: () -> Unit,
    modifier: Modifier = Modifier,
    onDetailClick: (Int) -> Unit = {},
    onEventClick: () -> Unit,
    onPesertaClick: () -> Unit,
    onTiketClick: () -> Unit,
    onTransaksiClick: () -> Unit,
    viewModel: HomeEventViewModel = viewModel(factory = PenyediaViewModelEvent.Factory)
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiHomeEvent.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                onRefresh = {
                    viewModel.getEvnt()},

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
                containerColor = Color(0xFFF48FB1), // Pink Floating Button
                modifier = Modifier.padding(18.dp)
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Event",
                    tint = MaterialTheme.colorScheme.onSecondary  )
            }
        },
    ) { innerPadding ->
        HomeEventStatus(
            homeEventUiState = viewModel.evntUiState,
            retryAction = { viewModel.getEvnt() }, modifier = Modifier.padding(innerPadding),
            onDetailClick = onDetailClick, onDeleteClick = {
                it.id_event?.let { id ->
                    viewModel.deleteEvnt(it.id_event)
                    viewModel.getEvnt()
                }
            })
    }}

@Composable
fun HomeEventStatus(
    homeEventUiState: HomeEventUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    onDeleteClick: (Event) -> Unit,
    onDetailClick: (Int) -> Unit
) {
    when (homeEventUiState) {
        is HomeEventUiState.Loading -> OnLoading(modifier = modifier.fillMaxSize())
        is HomeEventUiState.Success -> {
            if (homeEventUiState.event.isEmpty()) {
                Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "Tidak ada data Event", color = Color(0xFFD81B60))
                }
            } else {
                EventLayout(
                    event = homeEventUiState.event,
                    modifier = modifier.fillMaxWidth(),
                    onDetailClick = onDetailClick,
                    onDeleteClick = { onDeleteClick(it) }
                )
            }
        }
        is HomeEventUiState.Error -> OnError(retryAction, modifier = modifier.fillMaxSize())
    }
}

@Composable
fun OnLoading(modifier: Modifier = Modifier) {
    Image(
        modifier = modifier.size(200.dp),
        painter = painterResource(R.drawable.loading),
        contentDescription = stringResource(R.string.loading)
    )
}

@Composable
fun OnError(retryAction: () -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.error), contentDescription = ""
        )
        Text(text = stringResource(R.string.loading), color = Color(0xFFD81B60), modifier = Modifier.padding(16.dp))
        Button(onClick = retryAction, colors = androidx.compose.material3.ButtonDefaults.buttonColors(containerColor = Color(0xFFFFC1E3))) {
            Text(stringResource(R.string.retry))
        }
    }
}

@Composable
fun EventLayout(
    event: List<Event>,
    modifier: Modifier = Modifier,
    onDetailClick: (Int) -> Unit,
    onDeleteClick: (Event) -> Unit = {}
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(event) { event ->
            EventsCard(
                event = event,
                modifier = Modifier
                    .fillMaxWidth(),
                onDetailClick = onDetailClick,
                onDeleteClick = { onDeleteClick(it) }
            )
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)

@Composable
fun EventsCard(
    event: Event,
    modifier: Modifier = Modifier,
    onDetailClick: (Int) -> Unit,
    onDeleteClick: (Event) -> Unit = {}
) {
    Card(
        onClick = { onDetailClick(event.id_event) },
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFC1E3)),
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
                    text = event.nama_event,
                    style = MaterialTheme.typography.titleLarge,
                    color = Color(0xFFD81B60)
                )
                Spacer(Modifier.weight(1f))
                IconButton(onClick = { onDeleteClick(event) }) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = null,
                        tint = Color(0xFFD81B60)
                    )
                }
            }
            Text(// Menampilkan id_event
                text = "ID: ${event.id_event}",
                style = MaterialTheme.typography.titleMedium,
                color = Color(0xFFD81B60)
            )
            Text(
                text = event.deskripsi_event,
                style = MaterialTheme.typography.titleMedium,
                color = Color(0xFFD81B60)
            )
            Text(
                text = event.tanggal_event,
                style = MaterialTheme.typography.titleMedium,
                color = Color(0xFFD81B60)
            )
            Text(
                text = event.lokasi_event,
                style = MaterialTheme.typography.titleMedium,
                color = Color(0xFFD81B60)

            )
        }
    }
}
