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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.a13_179.ui.customwidget.CostumeTopAppBar
import com.example.a13_179.ui.navigation.DestinasiNavigasi
import com.example.a13_179.ui.viewmodel.event.PenyediaViewModel
import com.example.a13_179.R
import com.example.a13_179.model.Tiket
import com.example.a13_179.ui.viewmodel.tiket.HomeTiketUiState
import com.example.a13_179.ui.viewmodel.tiket.HomeTiketViewModel


object DestinasiHomeTiket: DestinasiNavigasi {
    override val route ="home_tiket"
    override val titleRes = "Home Tiket"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTiketScreen(
    navigateToItemEntryTiket:()->Unit,
    modifier: Modifier=Modifier,
    onDetailClickTiket: (Int) -> Unit ={},
    viewModel: HomeTiketViewModel = viewModel(factory = PenyediaViewModel.Factory)

){
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiHomeTiket.titleRes,
                canNavigateBack = false,
                scrollBehavior = scrollBehavior,
                onRefresh = {
                    viewModel.getTkt ()}
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToItemEntryTiket,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(18.dp)
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Tiket")
            }
        },
    ) { innerPadding->
        HomeTiketStatus(
            homeTiketUiState = viewModel.tktUIState,
            retryAction = {viewModel.getTkt()}, modifier = Modifier.padding(innerPadding),
            onDetailClickTiket = onDetailClickTiket,onDeleteClickTiket = {
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
    onDetailClickTiket: (Int) -> Unit
){
    when (homeTiketUiState){
        is HomeTiketUiState.Loading-> OnLoading(modifier = modifier.fillMaxSize())

        is HomeTiketUiState.Success ->
            if(homeTiketUiState.tiket.isEmpty()){
                return Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                    Text(text = "Tidak Ada Data Tiket")
                }
            }else{
                TiketLayout(
                    tiket = homeTiketUiState.tiket,modifier = modifier.fillMaxWidth(),
                    onDetailClickTiket = {
                        onDetailClickTiket(it.id_tiket)
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
        Text(text = stringResource(R.string.loading_failed), modifier = Modifier.padding(16.dp))
        Button(onClick = retryAction) {
            Text(stringResource(R.string.retry))
        }
    }
}

@Composable
fun TiketLayout( //TiketLayout utk menampilkan daftar tiket dengan komponen LazyColumn.
    tiket: List<Tiket>,
    modifier: Modifier = Modifier,
    onDetailClickTiket:(Tiket)->Unit,
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
                    .clickable{onDetailClickTiket(tiket)},
                onDeleteClickTiket ={
                    onDeleteClickTiket(tiket)
                }
            )

        }
    }
}

@Composable
fun TiketCard( //PesertaCard utk menampilkan detail peserta dengan opsi hapus
    tiket: Tiket,
    modifier: Modifier = Modifier,
    onDeleteClickTiket:(Tiket)->Unit={}
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
                    text = tiket.id_tiket.toString(),
                    style = MaterialTheme.typography.titleLarge)

                Spacer(Modifier.weight(1f))
                IconButton(onClick = { onDeleteClickTiket(tiket) }) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = null,
                    )
                }
            }
            Text(text = tiket.id_tiket.toString(),
                style = MaterialTheme.typography.titleLarge
            )
            Text(
                text = tiket.kapasitas_tiket,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = tiket.harga_tiket,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}