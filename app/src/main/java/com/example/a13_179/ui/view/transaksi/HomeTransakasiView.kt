package com.example.a13_179.ui.view.transaksi

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
import com.example.a13_179.ui.customwidget.CostumeTopAppBar
import com.example.a13_179.ui.navigation.DestinasiNavigasi
import com.example.a13_179.R
import com.example.a13_179.model.Transaksi
import com.example.a13_179.ui.customwidget.BottomAppBarDefaults
import com.example.a13_179.ui.viewmodel.tiket.PenyediaViewModelTransaksi
import com.example.a13_179.ui.viewmodel.transaksi.HomeTransaksiUiState
import com.example.a13_179.ui.viewmodel.transaksi.HomeTransaksiViewModel


object DestinasiHomeTransaksi: DestinasiNavigasi {
    override val route ="home_transaksi"
    override val titleRes = "Home Transaksi"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTransaksiScreen(
    onEventClick: () -> Unit,
    onPesertaClick: () -> Unit,
    onTiketClick: () -> Unit,
    onTransaksiClick: () -> Unit,
    navigateToItemEntry:()->Unit,
    modifier: Modifier = Modifier,
    onDetailClick: (Int) -> Unit ={},
    viewModel: HomeTransaksiViewModel = viewModel(factory = PenyediaViewModelTransaksi.Factory)

){
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiHomeTransaksi.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                onRefresh = {
                    viewModel.getTrnsksi ()}
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
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Tiket")
            }
        },
    ) { innerPadding->
        HomeTransaksiStatus(
            homeTransaksiUiState = viewModel.trnsksiUIState,
            retryAction = {viewModel.getTrnsksi()}, modifier = Modifier.padding(innerPadding),
            onDetailClick = onDetailClick,onDeleteClickTransaksi = {
                viewModel.deleteTrnsksi(it.id_transaksi)
                viewModel.getTrnsksi()
            }
        )
    }
}

@Composable
fun HomeTransaksiStatus(
    homeTransaksiUiState: HomeTransaksiUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    onDeleteClickTransaksi: (Transaksi) -> Unit,
    onDetailClick: (Int) -> Unit
){
    when (homeTransaksiUiState){
        is HomeTransaksiUiState.Loading-> OnLoading(modifier = modifier.fillMaxSize())

        is HomeTransaksiUiState.Success ->
            if(homeTransaksiUiState.transaksi.isEmpty()){
                return Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                    Text(text = "Tidak Ada Data Transaksi")
                }
            }else{
                TransaksiLayout(
                    transaksi = homeTransaksiUiState.transaksi,modifier = modifier.fillMaxWidth(),
                    onDetailClick = {
                        onDetailClick(it.id_transaksi)
                    },
                    onDeleteClickTransaksi={
                        onDeleteClickTransaksi(it)
                    }
                )
            }
        is HomeTransaksiUiState.Error -> OnError(retryAction, modifier = modifier.fillMaxSize()
        )
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
fun TransaksiLayout(
    transaksi: List<Transaksi>,
    modifier: Modifier = Modifier,
    onDetailClick:(Transaksi)->Unit,
    onDeleteClickTransaksi: (Transaksi) -> Unit = {}
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(transaksi) { transaksi ->
            TransaksiCard(
                transaksi = transaksi,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onDetailClick(transaksi) },
                onDeleteClickTransaksi = {
                    onDeleteClickTransaksi(transaksi)
                }
            )

        }
    }
}

@Composable
fun TransaksiCard( //PesertaCard utk menampilkan detail peserta dengan opsi hapus
    transaksi: Transaksi,
    modifier: Modifier = Modifier,
    onDeleteClickTransaksi:(Transaksi)->Unit={}
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
                Spacer(Modifier.weight(1f))
                IconButton(onClick = { onDeleteClickTransaksi(transaksi) }) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = null,
                    )
                }
            }
            Text(text = transaksi.id_tiket.toString(), style = MaterialTheme.typography.bodyMedium)
            Text(text = transaksi.tanggal_transaksi, style = MaterialTheme.typography.bodyMedium)
            Text(text = "Jumlah Pembayaran: Rp. ${transaksi.jumlah_pembayaran}", style = MaterialTheme.typography.bodyMedium)
        }
    }
