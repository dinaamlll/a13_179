package com.example.a13_179.ui.view.peserta

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
import com.example.a13_179.model.Peserta
import com.example.a13_179.ui.customwidget.CostumeTopAppBar
import com.example.a13_179.ui.navigation.DestinasiNavigasi
import com.example.a13_179.ui.viewmodel.event.PenyediaViewModel
import com.example.a13_179.ui.viewmodel.peserta.DetailPesertaUiState
import com.example.a13_179.ui.viewmodel.peserta.DetailPesertaViewModel


object DestinasiDetailPeserta : DestinasiNavigasi {
    override val route = "detail_peserta" // base route
    const val ID_PESERTA = "id_peserta" // Nama parameter untuk id
    val routesWithArg = "$route/{$ID_PESERTA}" // Route yang menerima id sebagai argumen
    override val titleRes = "Detail Peserta" // Title untuk halaman ini
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailPasienView(
    idPeserta: Int,
    modifier: Modifier = Modifier,
    viewModel: DetailPesertaViewModel = viewModel(factory = PenyediaViewModel.Factory),
    onEditClick: (Int) -> Unit = {},
    navigateBack:()->Unit,
) {
    Scaffold(
        topBar = {
            CostumeTopAppBar(
                title = DestinasiDetailPeserta.titleRes,
                canNavigateBack = true,

                navigateUp = navigateBack,
                onRefresh = { viewModel.getDetailPeserta() } // Trigger refresh action on refresh
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onEditClick(idPeserta) },
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit Mahasiswa"
                )
            }
        }
    ) { innerPadding ->
        val detailPesertaUiState by viewModel.detailPesertaUiState.collectAsState()

        BodyDetailPeserta(
            modifier = Modifier.padding(innerPadding),
            detailPesertaUiState = detailPesertaUiState,
            retryAction = { viewModel.getDetailPeserta() }
        )
    }
}
@Composable
fun BodyDetailPeserta(
    modifier: Modifier = Modifier,
    detailPesertaUiState: DetailPesertaUiState,
    retryAction: () -> Unit = {}
) {
    when (detailPesertaUiState) {
        is DetailPesertaUiState.Loading -> {
            // Menampilkan gambar loading saat data sedang dimuat
            OnLoading(modifier = modifier.fillMaxSize())
        }
        is DetailPesertaUiState.Success -> {
            // Menampilkan detail mahasiswa jika berhasil
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                ItemDetailPeserta(peserta = detailPesertaUiState.peserta)
            }
        }
        is DetailPesertaUiState.Error -> {
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
fun ItemDetailPeserta(
    peserta: Peserta
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            ComponentDetailMhs(judul = "Id Peserta", isinya = peserta.id_peserta.toString())
            Spacer(modifier = Modifier.padding(4.dp))
            ComponentDetailMhs(judul = "Nama Peserta", isinya = peserta.nama_peserta)
            Spacer(modifier = Modifier.padding(4.dp))
            ComponentDetailMhs(judul = "Email", isinya = peserta.email)
            Spacer(modifier = Modifier.padding(4.dp))
            ComponentDetailMhs(judul = "Nomor Telepon", isinya = peserta.nomor_telepon)
        }
    }
}

@Composable
fun ComponentDetailMhs(
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
