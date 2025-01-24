package com.example.a13_179.ui.view.peserta

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.a13_179.model.Peserta
import com.example.a13_179.ui.customwidget.CostumeTopAppBar
import com.example.a13_179.ui.navigation.DestinasiNavigasi
import com.example.a13_179.ui.view.event.OnError
import com.example.a13_179.ui.view.event.OnLoading
import com.example.a13_179.ui.viewmodel.event.PenyediaViewModel
import com.example.a13_179.ui.viewmodel.peserta.HomePesertaViewModel


object DestinasiHomePeserta: DestinasiNavigasi {
    override val route ="home_peserta"
    override val titleRes = "Home Peserta"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomePesertaScreen(
    navigateToPesertaEntry:()->Unit,
    modifier: Modifier=Modifier,
    onDetailClickPeserta: (String) -> Unit ={},
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
                onClick = navigateToPesertaEntry,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(18.dp)
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Peserta")
            }
        },
    ) { innerPadding->
        HomeStatus(
            homePesertaUiState = viewModel.PesertaUIState,
            retryAction = {viewModel.getPsrta()}, modifier = Modifier.padding(innerPadding),
            onDetailClickPeserta = onDetailClickPeserta,onDeleteClickPeserta = {
                viewModel.deletePsrta(it.nim)
                viewModel.getPsrta()
            }
        )
    }
}
@Composable
fun HomeStatus(
    homePesertaUiState: HomePesertaUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    onDeleteClickPeserta: (Peserta) -> Unit = {},
    onDetailClickPeserta: (String) -> Unit
){
    when (homePesertaUiState){
        is HomePesertaUiState.Loading-> OnLoading(modifier = modifier.fillMaxSize())

        is HomePesertaUiState.Success ->
            if(homeUiState.peserta.isEmpty()){
                return Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                    Text(text = "Tidak ada data kontak")
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


