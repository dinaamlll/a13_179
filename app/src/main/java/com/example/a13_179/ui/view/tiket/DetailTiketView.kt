package com.example.a13_179.ui.view.tiket

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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.a13_179.model.Tiket
import com.example.a13_179.ui.customwidget.CostumeTopAppBar
import com.example.a13_179.ui.navigation.DestinasiNavigasi
import com.example.a13_179.ui.viewmodel.event.PenyediaViewModel
import com.example.a13_179.ui.viewmodel.tiket.DetailTiketUiState
import com.example.a13_179.ui.viewmodel.tiket.DetailTiketViewModel


object DestinasiDetailTiket : DestinasiNavigasi {
    override val route = "detail_tiket" // base route
    const val ID_TIKET = "id_tiket" // Nama parameter untuk id
    val routesWithArg = "$route/{$ID_TIKET}" // Route yang menerima id sebagai argumen
    override val titleRes = "Detail Tiket" // Title untuk halaman ini
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailTiketView(
    idTiket: Int,
    modifier: Modifier = Modifier,
    viewModel: DetailTiketViewModel = viewModel(factory = PenyediaViewModel.Factory),
    onEditClick: (Int) -> Unit = {},
    navigateBack:()->Unit,
) {
    Scaffold(
        topBar = {
            CostumeTopAppBar(
                title = DestinasiDetailTiket.titleRes,
                canNavigateBack = true,

                navigateUp = navigateBack,
                onRefresh = { viewModel.getDetailTiket() } // Trigger refresh action on refresh
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onEditClick(idTiket) },
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit Tiket"
                )
            }
        }
    ) { innerPadding ->
        val detailTiketUiState by viewModel.detailTiketUiState.collectAsState()

        BodyDetailTiket(
            modifier = Modifier.padding(innerPadding),
            detailTiketUiState = detailTiketUiState,
            retryAction = { viewModel.getDetailTiket() }
        )
    }
}
@Composable
fun BodyDetailTiket(
    modifier: Modifier = Modifier,
    detailTiketUiState: DetailTiketUiState,
    retryAction: () -> Unit = {}
) {
    when (detailTiketUiState) {
        is DetailTiketUiState.Loading -> { OnLoading(modifier = modifier.fillMaxSize())
        }
        is DetailTiketUiState.Success -> {
            // Menampilkan detail Tiket jika berhasil
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                ItemDetailTiket(tiket = detailTiketUiState.tiket)
            }
        }
        is DetailTiketUiState.Error -> { OnError(
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
fun ItemDetailTiket(
    tiket: Tiket
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            ComponentDetailTkt(judul = "Id Tiket", isinya = tiket.id_tiket.toString())
            Spacer(modifier = Modifier.padding(4.dp))
            ComponentDetailTkt(judul = "Kapasitas Tiket", isinya = tiket.kapasitas_tiket)
            Spacer(modifier = Modifier.padding(4.dp))
            ComponentDetailTkt(judul = "Harga Tiket", isinya = tiket.harga_tiket)
        }
    }
}

@Composable
fun ComponentDetailTkt(
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
