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
import com.example.a13_179.R
import com.example.a13_179.model.Peserta
import com.example.a13_179.ui.customwidget.CostumeTopAppBar
import com.example.a13_179.ui.navigation.DestinasiNavigasi
import com.example.a13_179.ui.view.event.OnError
import com.example.a13_179.ui.view.event.OnLoading
import com.example.a13_179.ui.viewmodel.event.PenyediaViewModel
import com.example.a13_179.ui.viewmodel.peserta.HomePesertaUiState
import com.example.a13_179.ui.viewmodel.peserta.HomePesertaViewModel



object DestinasiHomePeserta: DestinasiNavigasi {
    override val route ="home_peserta"
    override val titleRes = "Home Peserta"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomePesertaScreen(
    navigateToItemEntryPeserta:()->Unit,
    modifier: Modifier=Modifier,
        onDetailClickPeserta: (Int) -> Unit ={},
    viewModel: HomePesertaViewModel = viewModel(factory = PenyediaViewModel.Factory)

){
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiHomePeserta.titleRes,
                canNavigateBack = false,
                scrollBehavior = scrollBehavior,
                onRefresh = {
                    viewModel.getPsrta()
                }
            )
        },
        floatingActionButton = { //`FloatingActionButton` untuk navigasi ke entri peserta.
            FloatingActionButton(
                onClick = navigateToItemEntryPeserta,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(18.dp)
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Peserta")
            }
        },
    ) { innerPadding->
        HomePesertaStatus(
            homePesertaUiState = viewModel.psrtaUIState,
            retryAction = {viewModel.getPsrta()}, modifier = Modifier.padding(innerPadding),
            onDetailClickPeserta = onDetailClickPeserta,onDeleteClickPeserta = {
                viewModel.deletePsrta(it.id_peserta)
                viewModel.getPsrta()
            }
        )
    }
}
@Composable
fun HomePesertaStatus(
    homePesertaUiState: HomePesertaUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    onDeleteClickPeserta: (Peserta) -> Unit,
    onDetailClickPeserta: (Int) -> Unit
){
    when (homePesertaUiState){
        is HomePesertaUiState.Loading-> OnLoading(modifier = modifier.fillMaxSize())

        is HomePesertaUiState.Success ->
            if(homePesertaUiState.peserta.isEmpty()){
                return Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                    Text(text = "Tidak Ada Data Peserta")
                }
            }else{
                PesertaLayout(
                    peserta = homePesertaUiState.peserta,modifier = modifier.fillMaxWidth(),
                    onDetailClickPeserta = {
                        onDetailClickPeserta(it.id_peserta)
                    },
                    onDeleteClickPeserta={
                        onDeleteClickPeserta(it)
                    }
                )
            }
        is HomePesertaUiState.Error -> OnError(retryAction, modifier = modifier.fillMaxSize())
    }
}
/**
 * OnLoading menampilkan animasi/gambar saat proses loading.
 */
@Composable
fun OnLoading(modifier: Modifier = Modifier){
    Image(
        modifier = modifier.size(200.dp),
        painter = painterResource(R.drawable.loading),
        contentDescription = stringResource(R.string.loading)
    )
}

/**
 * OnError menampilkan pesan error dengan tombol retry untuk memuat ulang data.
 */

@Composable
fun OnError(retryAction:()->Unit, modifier: Modifier = Modifier){
    Column(
        modifier=modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.error), contentDescription = ""
        )
        Text(text = stringResource(R.string.loading_failed), modifier = Modifier.padding(16.dp))
        Button(onClick = retryAction) {
            Text(stringResource(R.string.retry))
        }
    }
}

@Composable
fun PesertaLayout( //PesertaLayout utk menampilkan daftar peserta dengan komponen LazyColumn.
    peserta: List<Peserta>,
    modifier: Modifier = Modifier,
    onDetailClickPeserta:(Peserta)->Unit,
    onDeleteClickPeserta: (Peserta) -> Unit = {}
){
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(peserta){ peserta ->
            PesertaCard(
                peserta = peserta,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable{onDetailClickPeserta(peserta)},
                onDeleteClickPeserta ={
                    onDeleteClickPeserta(peserta)
                }
            )

        }
    }
}

@Composable
fun PesertaCard( //PesertaCard utk menampilkan detail peserta dengan opsi hapus
    peserta: Peserta,
    modifier: Modifier = Modifier,
    onDeleteClickPeserta:(Peserta)->Unit={}
){
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
                    text = peserta.nama_peserta,
                    style = MaterialTheme.typography.titleLarge
                )
                Spacer(Modifier.weight(1f))
                IconButton(onClick = { onDeleteClickPeserta(peserta) }) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = null,
                    )
                }
            }
                // Menampilkan id_event
                Text(
                    text = "ID: ${peserta.id_peserta}",
                    style = MaterialTheme.typography.titleMedium
                )
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