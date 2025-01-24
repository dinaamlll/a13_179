package com.example.a13_179.ui.view.event


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import com.example.a13_179.model.Event
import com.example.a13_179.ui.customwidget.CostumeTopAppBar
import com.example.a13_179.ui.navigation.DestinasiNavigasi
import com.example.a13_179.ui.viewmodel.event.DetaiEventlUiState
import com.example.a13_179.ui.viewmodel.event.DetailEventViewModel
import com.example.a13_179.ui.viewmodel.event.PenyediaViewModel


object DestinasiDetailEvent : DestinasiNavigasi {
    override val route = "detail_event"
    const val ID_EVENT = "id_event"
    val routesWithArg = "$route/{$ID_EVENT}" // Route yang menerima nim sebagai argumen
    override val titleRes = "Detail Event"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetaiEventlView(
    IdEvent: Int,
    modifier: Modifier = Modifier,
    viewModel: DetailEventViewModel = viewModel(factory = PenyediaViewModel.Factory),
    onEditClick: (Int) -> Unit = {},
    navigateBack:()->Unit,
) {
    val detailEventUiState by viewModel.detaiEventlUiState.collectAsState()
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
            if (detailEventUiState is DetaiEventlUiState.Success) { // Ditambahkan
                val event = (detailEventUiState as DetaiEventlUiState.Success).event
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
        val detailEventUiState by viewModel.detaiEventlUiState.collectAsState()

        BodyDetailMhs(
            modifier = Modifier.padding(innerPadding),
            detailEventUiState = detailEventUiState,
            retryAction = { viewModel.getDetailEvent() }
        )
    }
}

@Composable
fun BodyDetailMhs(
    modifier: Modifier = Modifier,
    detailEventUiState: DetaiEventlUiState,
    retryAction: () -> Unit = {}
) {
    when (detailEventUiState) {
        is DetaiEventlUiState.Loading -> {
            // Menampilkan gambar loading saat data sedang dimuat
            OnLoading(modifier = modifier.fillMaxSize())
        }
        is DetaiEventlUiState.Success -> {
            // Menampilkan detail mahasiswa jika berhasil
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                ItemDetailEvents(event = detailEventUiState.event)
            }
        }
        is DetaiEventlUiState.Error -> {
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