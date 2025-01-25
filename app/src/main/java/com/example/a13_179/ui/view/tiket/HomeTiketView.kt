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
import com.example.a13_179.model.Event
import com.example.a13_179.ui.customwidget.CostumeTopAppBar
import com.example.a13_179.ui.navigation.DestinasiNavigasi
import com.example.a13_179.ui.viewmodel.event.HomeEventUiState
import com.example.a13_179.ui.viewmodel.event.HomeEventViewModel
import com.example.a13_179.ui.viewmodel.event.PenyediaViewModel
import com.example.a13_179.R
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
    onDetailClick: (Int) -> Unit ={},
    viewModel: HomeTiketViewModel = viewModel(factory = PenyediaViewModel.Factory)

){
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiTiketEvent.titleRes,
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
        HomeEventStatus(
            homeTiketUiState = viewModel.tiketUIState,
            retryAction = {viewModel.getTkt()}, modifier = Modifier.padding(innerPadding),
            onDetailClickTiket = onDetailClickTiket,onDeleteClickTiket = {
                viewModel.deleteTkt(it.id_tiket)
                viewModel.getTkt()
            }
        )
    }
}

@Composable
fun HomeEventStatus(
    homeEventUiState: HomeEventUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    onDeleteClick: (Event) -> Unit,
    onDetailClick: (Int) -> Unit
){
    when (homeEventUiState){
        is HomeEventUiState.Loading-> OnLoading(modifier = modifier.fillMaxSize())

        is HomeEventUiState.Success ->
            if(homeEventUiState.event.isEmpty()){
                Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                    Text(text = "Tidak ada data Event")
                }
            }else{
                EventsLayout(
                    event = homeEventUiState.event,modifier = modifier.fillMaxWidth(),
                    onDetailClick = { onDetailClick(it.id_event) },
                    onDeleteClick={
                        onDeleteClick(it)
                    }
                )
            }
        is HomeEventUiState.Error -> OnError(retryAction, modifier = modifier.fillMaxSize())
        else -> {
            // Default handling jika ada tipe baru
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Status tidak dikenal.")
            }
        }
    }
}
/**
 * The home screen displaying the loading message.
 */
@Composable
fun OnLoading(modifier: Modifier = Modifier){
    Image(
        modifier = modifier.size(200.dp),
        painter = painterResource(id = R.drawable.loading),
        contentDescription = stringResource(R.string.loading)
    )
}

/**
 * The home screen displaying error message with re-attempt button.
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
fun EventsLayout(
    event: List<Event>,
    modifier: Modifier = Modifier,
    onDetailClick:(Event)->Unit,
    onDeleteClick: (Event) -> Unit = {}
){
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(event){ event ->
            EventsCard(
                event = event,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onDetailClick(event) },
                onDeleteClick ={
                    onDeleteClick(event)
                }
            )

        }
    }
}

@Composable
fun EventsCard(
    event: Event,
    modifier: Modifier = Modifier,
    onDeleteClick:(Event)->Unit={}
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
                    text = event.nama_event,
                    style = MaterialTheme.typography.titleLarge
                )
                Spacer(Modifier.weight(1f))
                IconButton(onClick = { onDeleteClick(event) }) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = null,
                    )
                }
            }
            // Menampilkan id_event
            Text(
                text = "ID: ${event.id_event}",
                style = MaterialTheme.typography.titleMedium
            )


            Text(
                text = event.tanggal_event,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = event.lokasi_event,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}