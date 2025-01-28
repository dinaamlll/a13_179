package com.example.a13_179.ui.view.event



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
import com.example.a13_179.model.Event
import com.example.a13_179.ui.customwidget.BottomAppBarDefaults
import com.example.a13_179.ui.customwidget.CostumeTopAppBar
import com.example.a13_179.ui.navigation.DestinasiNavigasi
import com.example.a13_179.ui.view.peserta.DestinasiDetailPeserta
import com.example.a13_179.ui.viewmodel.event.DetailEventUiState
import com.example.a13_179.ui.viewmodel.event.DetailEventViewModel
import com.example.a13_179.ui.viewmodel.event.PenyediaViewModelEvent

object DestinasiDetailEvent : DestinasiNavigasi {
    override val route = "detail/{id_event}"
    const val id_event = "id_event"
   // val routeWithArgs = route // Tidak perlu redundansi
    override val titleRes = "Detail Event"
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailEventScreen(
    id_event: Int,  // Change here
    onEventClick: () -> Unit,
    onPesertaClick: () -> Unit,
    onTiketClick: () -> Unit,
    onTransaksiClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DetailEventViewModel = viewModel(factory = PenyediaViewModelEvent.Factory),
    onEditClick: (Int) -> Unit,  // Change here
    onBackClick: () -> Unit
) {
    val event = viewModel.uiState.detailEventUiEvent

    LaunchedEffect(id_event) {
        viewModel.getDetailEvent(id_event)
    }

    val isLoading = viewModel.uiState.isLoading
    val isError = viewModel.uiState.isError
    val errorMessage = viewModel.uiState.errorMessage
    Scaffold(
        topBar = {
            CostumeTopAppBar(
                title = DestinasiDetailEvent.titleRes,
                canNavigateBack = true,

                navigateUp = navigateBack,
                onRefresh = { viewModel.getDetailEvent() } // Trigger refresh action on refresh
            )
        },
        floatingActionButton = {
            if (detailEventUiState is DetailEventlUiState.Success) { // Ditambahkan
                val event = (detailEventUiState as DetailEventlUiState.Success).event
                FloatingActionButton(
                    onClick = { onEditClick(IdEvent) },
                    shape = MaterialTheme.shapes.medium,
                    modifier = Modifier.padding(16.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Edit Mahasiswa"
                    )
                }
            }
        }
    ) { innerPadding ->
        val detailEventUiState by viewModel.detailEventlUiState.collectAsState()

        BodyDetailEvent(
            modifier = Modifier.padding(innerPadding),
            detailEventUiState = detailEventUiState,
            retryAction = { viewModel.getDetailEvent() }
        )
    }
}

@Composable
fun BodyDetailEvent(
    modifier: Modifier = Modifier,
    detailEventUiState: DetailEventlUiState,
    retryAction: () -> Unit = {}
) {
    when (detailEventUiState) {
        is DetailEventlUiState.Loading -> {
            // Menampilkan gambar loading saat data sedang dimuat
            OnLoading(modifier = modifier.fillMaxSize())
        }
        is DetailEventlUiState.Success -> {
            // Menampilkan detail mahasiswa jika berhasil
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                ItemDetailEvents(event = detailEventUiState.event)
            }
        }
        is DetailEventlUiState.Error -> {
            // Menampilkan error jika data gagal dimuat
            OnError(
                retryAction = retryAction,
                modifier = modifier.fillMaxSize()
            )
        }
        else -> {
            // Menangani kasus yang tidak terduga (optional, jika Anda ingin menangani hal ini)
            // Anda bisa menambahkan logika untuk menangani kesalahan yang tidak diketahui
            Text("Unexpected state encountered")
        }
    }

}

@Composable
fun ItemDetailEvents(
    event: Event
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            ComponentDetailEvents(judul = "Id Event", isinya = event.id_event.toString())
            Spacer(modifier = Modifier.padding(4.dp))
            ComponentDetailEvents(judul = "Nama Event", isinya = event.nama_event)
            Spacer(modifier = Modifier.padding(4.dp))
            ComponentDetailEvents(judul = "Deskripsi Event", isinya = event.deskripsi_event)
            Spacer(modifier = Modifier.padding(4.dp))
            ComponentDetailEvents(judul = "Tanggal Event", isinya = event.tanggal_event)
            Spacer(modifier = Modifier.padding(4.dp))
            ComponentDetailEvents(judul = "Lokasi Event", isinya = event.lokasi_event)

        }
    }
}

@Composable
fun ComponentDetailEvents(
    judul: String,
    isinya: String
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "$judul :",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Gray
        )
        Text(
            text = isinya,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
    }
}