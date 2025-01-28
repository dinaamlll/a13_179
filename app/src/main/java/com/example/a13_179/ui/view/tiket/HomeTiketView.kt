package com.example.a13_179.ui.view.tiket

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.a13_179.ui.customwidget.CostumeTopAppBar
import com.example.a13_179.ui.navigation.DestinasiNavigasi
import com.example.a13_179.R
import com.example.a13_179.model.Tiket
import com.example.a13_179.ui.customwidget.BottomAppBarDefaults
import com.example.a13_179.ui.view.event.DestinasiHomeEvent
import com.example.a13_179.ui.viewmodel.tiket.HomeTiketUiState
import com.example.a13_179.ui.viewmodel.tiket.HomeTiketViewModel
import com.example.a13_179.ui.viewmodel.tiket.PenyediaViewModelTiket


object DestinasiHomeTiket: DestinasiNavigasi {
    override val route ="home_tiket"
    override val titleRes = "Selamat Datang di Halaman Tiket"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTiketScreen(
    onDetailClick: (Int) -> Unit = {},
    onEventClick: () -> Unit,
    onPesertaClick: () -> Unit,
    onTiketClick: () -> Unit,
    onTransaksiClick: () -> Unit,
    navigateToItemEntry: () -> Unit,
    modifier: Modifier=Modifier,
    viewModel: HomeTiketViewModel = viewModel(factory = PenyediaViewModelTiket.Factory),

    ){

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiHomeTiket.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                onRefresh = {
                    viewModel.getTkt ()}
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
                modifier = Modifier.padding(18.dp),
                containerColor = Color(0xFFF48FB1),
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Tiket",
                    tint = MaterialTheme.colorScheme.onSecondary  )
            }
        },
    ) { innerPadding->
        HomeTiketStatus(
            homeTiketUiState = viewModel.tktUIState,
            retryAction = {viewModel.getTkt()}, modifier = Modifier.padding(innerPadding),
            onDetailClick = onDetailClick,onDeleteClickTiket = {
                viewModel.deleteTkt(it.id_tiket)
                viewModel.getTkt()
            }
        )
    }
}

@Composable
fun HomeTiketStatus(
    homeTiketUiState: HomeTiketUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    onDeleteClickTiket: (Tiket) -> Unit,
    onDetailClick: (Int) -> Unit
){
    when (homeTiketUiState){
        is HomeTiketUiState.Loading-> OnLoading(modifier = modifier.fillMaxSize())

        is HomeTiketUiState.Success ->
            if(homeTiketUiState.tiket.isEmpty()){
                return Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                    Text(text = "Tidak Ada Data Tiket",color = Color(0xFFD81B60))
                }
            }else{
                TiketLayout(
                    tiket = homeTiketUiState.tiket,modifier = modifier.fillMaxWidth(),
                    onDetailClick = {
                        onDetailClick(it.id_tiket)
                    },
                    onDeleteClickTiket={
                        onDeleteClickTiket(it)
                    }
                )
            }
        is HomeTiketUiState.Error -> OnError(retryAction, modifier = modifier.fillMaxSize()
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
        Text(text = stringResource(R.string.loading_failed),color = Color(0xFFD81B60), modifier = Modifier.padding(16.dp))
        Button(onClick = retryAction, colors = androidx.compose.material3.ButtonDefaults.buttonColors(containerColor = Color(0xFFFFC1E3))) {
            Text(stringResource(R.string.retry))
        }
    }
}

@Composable
fun TiketLayout( //TiketLayout utk menampilkan daftar tiket dengan komponen LazyColumn.
    tiket: List<Tiket>,
    modifier: Modifier = Modifier,
    onDetailClick:(Tiket)->Unit,
    onDeleteClickTiket: (Tiket) -> Unit = {}
){
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(tiket){ tiket ->
            TiketCard(
                tiket = tiket,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable{onDetailClick(tiket)},
                onDeleteClickTiket={
                    onDeleteClickTiket(tiket)
                }
            )

        }
    }
}

@Composable
fun TiketCard( //TiketCard utk menampilkan detail tiket dengan opsi hapus
    tiket: Tiket,
    modifier: Modifier = Modifier,
    onDeleteClickTiket:(Tiket)->Unit={}
){
    Card(
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
                    text = tiket.id_tiket.toString(),
                    style = MaterialTheme.typography.titleLarge,
                    color = Color(0xFFD81B60)
                )
                Spacer(Modifier.weight(1f))
                IconButton(onClick = { onDeleteClickTiket(tiket) }) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = null,
                    )
                }
            }
            Text(text = tiket.id_tiket.toString(),
                style = MaterialTheme.typography.titleMedium,
                color = Color(0xFFD81B60)
            )
            Text(
                text = tiket.kapasitas_tiket.toString(),
                style = MaterialTheme.typography.titleMedium,
                color = Color(0xFFD81B60)
            )
            Text(
                text = tiket.harga_tiket.toString(),
                style = MaterialTheme.typography.titleMedium,
                color = Color(0xFFD81B60)
            )
        }
    }
}
